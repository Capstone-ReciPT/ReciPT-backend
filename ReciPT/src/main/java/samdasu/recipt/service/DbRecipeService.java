package samdasu.recipt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.controller.dto.DbDto;
import samdasu.recipt.entity.DbRecipe;
import samdasu.recipt.exception.ResourceNotFoundException;
import samdasu.recipt.repository.DbRecipe.DbRecipeRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DbRecipeService {
    private final DbRecipeRepository dbRecipeRepository;

    /**
     * 평점 계산
     */

    /**
     * DB 레시피는 init 넣고 터치 X
     * - 필요없는 함수임!
     */
    @Transactional
    public Long dbSave(DbDto dbDto) {
        DbRecipe dbRecipe = DbRecipe.createDbRecipe(dbDto.getDbFoodName(), dbDto.getDbIngredient(), dbDto.getHowToCook(), dbDto.getThumbnailImage(), dbDto.getDbContext(), dbDto.getDbImage(), dbDto.getDbLikeCount(), dbDto.getDbViewCount(), dbDto.getAllergy());
        return dbRecipeRepository.save(dbRecipe).getDbRecipeId();
    }

    /**
     * 조회 수 탑 10 조회
     */
    public List<DbRecipe> findTop10ViewCount(DbRecipe dbRecipe) {
        return dbRecipeRepository.Top10DbRecipeLike(dbRecipe);
    }

    public DbDto findById(Long dbRecipeId) {
        DbRecipe dbRecipe = dbRecipeRepository.findById(dbRecipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail:No dbRecipe Info"));
        return new DbDto(dbRecipe);
    }

    public DbDto findByFoodName(String dbFoodName) {
        DbRecipe dbRecipe = dbRecipeRepository.findByDbFoodName(dbFoodName)
                .orElseThrow(() -> new ResourceNotFoundException("Fail:No dbRecipe Info"));
        return new DbDto(dbRecipe);
    }

    public List<DbRecipe> findAll() {
        return dbRecipeRepository.findAll();
    }
}