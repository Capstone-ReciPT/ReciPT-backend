package samdasu.recipt.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.entity.DbRecipe;
import samdasu.recipt.entity.Heart;
import samdasu.recipt.repository.DbRecipe.DbRecipeRepository;
import samdasu.recipt.repository.GptRecipe.GptRecipeRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DbRecipeServiceTest {
    @Autowired
    DbRecipeRepository dbRecipeRepository;
    @Autowired
    DbRecipeService dbRecipeService;
    @Autowired
    GptRecipeRepository gptRecipeRepository;

    @Test
    @Rollback(value = false)
    public void DB_조회수_탑_10() throws Exception {
        //given

        //when
        System.out.println("############### 조회수 top10 ###############");
        List<DbRecipe> top10ViewCount = dbRecipeService.findTop10ViewCount();
        //then
        for (DbRecipe recipe : top10ViewCount) {
            System.out.println("dbRecipe.getDbViewCount() = " + recipe.getDbViewCount());
        }
    }

    @Test
    public void DB_좋아요_탑_10() throws Exception {
        //given

        //when
        System.out.println("############### 좋아요 수 top10 ###############");
        List<DbRecipe> top10LikeCount = dbRecipeService.findTop10LikeCount();
        //then
        for (DbRecipe recipe : top10LikeCount) {
            System.out.println("dbRecipe.getDbLikeCount() = " + recipe.getDbLikeCount());
        }
    }

    @Test
    public void DB_레시피_음식이름_단건조회() throws Exception {
        //given

        //when
        DbRecipe dbRecipe = dbRecipeService.findByFoodName("새우두부계란찜");

        //then
        assertThat(dbRecipe.getDbFoodName()).isEqualTo("새우두부계란찜");
    }

    @Test
    public void DB_레시피_전체조회() throws Exception {
        //given

        //when
        List<DbRecipe> dbRecipes = dbRecipeService.findAll();

        //then
        Assertions.assertThat(dbRecipes.size()).isEqualTo(14);
    }

    @PersistenceContext
    EntityManager em;

    @Test
    @Rollback(value = false)
    public void DB_GPT_조회수_탑10() throws Exception {
        //given

//        //when
//        Page<RecipeProjection> result = dbRecipeRepository.Top10AllRecipeLike(PageRequest.of(0, 10));
//        List<RecipeProjection> content = result.getContent();
//        for (RecipeProjection recipeProjection : content) {
//            System.out.println("recipeProjection = " + recipeProjection.getFoodName());
//            System.out.println("recipeProjection.getIngredient() = " + recipeProjection.getIngredient());
//        }
//        //then

        List<Heart> hearts = dbRecipeRepository.Top10AllRecipeLike();
        for (Heart heart : hearts) {
            System.out.println("heart = " + heart);
        }
    }
}