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
    public Long insertDbHeart(DbHeartDto heartDto) {
        User user = getUser(heartDto, "Fail: No User Info");
        DbRecipe dbRecipe = getDbRecipe(heartDto, "Fail: No DbRecipe Info");

        //이미 좋아요되어있으면 에러 반환
//        if (heartRepository.findByUserAndDbRecipe(user, dbRecipe).isPresent()) {
//            //TODO 409에러로 변경
//            throw new DuplicateResourceException("already exist data by user id :" + user.getUserId() + " ,"
//                    + "dbRecipe id : " + dbRecipe.getDbRecipeId());
//        }

        Heart heart = Heart.createDbHeart(user, dbRecipe);
        heartRepository.save(heart);
        dbRecipeRepository.addDbLikeCount(dbRecipe);

        return heart.getHeartId();
    }

    @Transactional
    public void deleteDbHeart(DbHeartDto dbHeartDto) {
        User user = getUser(dbHeartDto, "Could not found user id : " + dbHeartDto.getUserId());
        DbRecipe dbRecipe = getDbRecipe(dbHeartDto, "Could not found DbRecipe id : " + dbHeartDto.getDbRecipeId());
        userRepository.save(user);
        dbRecipeRepository.save(dbRecipe);

        Heart heart = heartRepository.findUserAndDbRecipe(user.getUserId(), dbRecipe.getDbRecipeId())
                .orElseThrow(() -> new ResourceNotFoundException("Could not found heart"));

        System.out.println("heart.getUser().getusername() = " + heart.getUser().getHearts());
        System.out.println("heart.getDbRecipe().getDbFoodName() = " + heart.getDbRecipe().getDbFoodName());

        heartRepository.delete(heart);
        dbRecipeRepository.subDbLikeCount(dbRecipe);
    }

    @Transactional
    public void insertGptHeart(GptHeartDto heartDto) {
        User user = getUser(heartDto, "Fail: No User Info");
        GptRecipe gptRecipe = getGptRecipe(heartDto, "Fail: No GptRecipe Info");

        if (heartRepository.findByUserAndGptRecipe(user, gptRecipe).isPresent()) {
            throw new DuplicateResourceException("already exist data by user id :" + user.getUserId() + " ,"
                    + "gptRecipe id : " + gptRecipe.getGptRecipeId());
        }

        Heart heart = Heart.createGptHeart(user, gptRecipe);
        heartRepository.save(heart);

        userRepository.save(user).getUserId();
        gptRecipeRepository.addGptLikeCount(gptRecipe);
    }

    @Transactional
    public void deleteGptHeart(GptHeartDto heartDto) {
        User user = getUser(heartDto, "Could not found user id : " + heartDto.getUserId());
        GptRecipe gptRecipe = getGptRecipe(heartDto, "Could not found GptRecipe id : " + heartDto.getGptRecipeId());

        Heart heart = heartRepository.findByUserAndGptRecipe(user, gptRecipe)
                .orElseThrow(() -> new ResourceNotFoundException("Could not found heart id"));

        heartRepository.delete(heart);
        gptRecipeRepository.subGptLikeCount(gptRecipe);
    }

    private User getUser(DbHeartDto heartDto, String message) {
        User user = userRepository.findById(heartDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(message));
        return user;
    }

    private User getUser(GptHeartDto heartDto, String message) {
        User user = userRepository.findById(heartDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(message));
        return user;
    }

    private DbRecipe getDbRecipe(DbHeartDto heartDto, String message) {
        DbRecipe dbRecipe = dbRecipeRepository.findById(heartDto.getDbRecipeId())
                .orElseThrow(() -> new ResourceNotFoundException(message));
        return dbRecipe;
    }

    private GptRecipe getGptRecipe(GptHeartDto heartDto, String message) {
        GptRecipe gptRecipe = gptRecipeRepository.findById(heartDto.getGptRecipeId())
                .orElseThrow(() -> new ResourceNotFoundException(message));
        return gptRecipe;
    }
}
