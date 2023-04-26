package samdasu.recipt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.controller.dto.Heart.DbHeartDto;
import samdasu.recipt.controller.dto.Heart.GptHeartDto;
import samdasu.recipt.entity.DbRecipe;
import samdasu.recipt.entity.GptRecipe;
import samdasu.recipt.entity.Heart;
import samdasu.recipt.entity.User;
import samdasu.recipt.exception.DuplicateResourceException;
import samdasu.recipt.exception.ResourceNotFoundException;
import samdasu.recipt.repository.DbRecipe.DbRecipeRepository;
import samdasu.recipt.repository.GptRecipe.GptRecipeRepository;
import samdasu.recipt.repository.HeartRepository;
import samdasu.recipt.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class HeartService {
    private final HeartRepository heartRepository;
    private final UserRepository userRepository;
    private final DbRecipeRepository dbRecipeRepository;
    private final GptRecipeRepository gptRecipeRepository;

    @Transactional
    public void insertDbHeart(DbHeartDto heartDto) {
        User user = getUser(heartDto.getUserId());
        DbRecipe dbRecipe = getDbRecipe(heartDto);

        //이미 좋아요되어있으면 에러 반환
        if (heartRepository.findByUserAndDbRecipe(user, dbRecipe).isPresent()) {
            //TODO 409에러로 변경
            throw new DuplicateResourceException("already exist data by user id :" + user.getUserId() + " ,"
                    + "dbRecipe id : " + dbRecipe.getDbRecipeId());
        }

        Heart heart = Heart.createDbHeart(user, dbRecipe);
        heartRepository.save(heart);
        dbRecipeRepository.addDbLikeCount(dbRecipe);
    }


    @Transactional
    public void deleteDbHeart(DbHeartDto dbHeartDto) {
        User user = getUser(dbHeartDto.getUserId());
        DbRecipe dbRecipe = getDbRecipe(dbHeartDto);

        Heart heart = heartRepository.findByUserAndDbRecipe(user, dbRecipe)
                .orElseThrow(() -> new ResourceNotFoundException("Could not found heart"));

        heartRepository.delete(heart);
        dbRecipeRepository.subDbLikeCount(dbRecipe);
    }

    @Transactional
    public void insertGptHeart(GptHeartDto heartDto) {
        User user = getUser(heartDto.getUserId());
        GptRecipe gptRecipe = getGptRecipe(heartDto);

        if (heartRepository.findByUserAndGptRecipe(user, gptRecipe).isPresent()) {
            throw new DuplicateResourceException("already exist data by user id :" + user.getUserId() + " ,"
                    + "gptRecipe id : " + gptRecipe.getGptRecipeId());
        }

        Heart heart = Heart.createGptHeart(user, gptRecipe);
        heartRepository.save(heart);
        gptRecipeRepository.addGptLikeCount(gptRecipe);
    }

    @Transactional
    public void deleteGptHeart(GptHeartDto gptHeartDto) {
        User user = getUser(gptHeartDto.getUserId());
        GptRecipe gptRecipe = getGptRecipe(gptHeartDto);

        Heart heart = heartRepository.findByUserAndGptRecipe(user, gptRecipe)
                .orElseThrow(() -> new ResourceNotFoundException("Could not found heart"));

        heartRepository.delete(heart);
        gptRecipeRepository.subGptLikeCount(gptRecipe);
    }

    private User getUser(Long heartDto) {
        User user = userRepository.findById(heartDto)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No User Info"));
        return user;
    }

    private DbRecipe getDbRecipe(DbHeartDto heartDto) {
        DbRecipe dbRecipe = dbRecipeRepository.findById(heartDto.getDbRecipeId())
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No DbRecipe Info"));
        return dbRecipe;
    }

    private GptRecipe getGptRecipe(GptHeartDto gptHeartDto) {
        GptRecipe gptRecipe = gptRecipeRepository.findById(gptHeartDto.getGptRecipeId())
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No GptRecipe Info"));
        return gptRecipe;
    }
}
