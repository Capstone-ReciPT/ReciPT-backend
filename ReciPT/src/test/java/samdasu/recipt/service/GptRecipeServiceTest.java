package samdasu.recipt.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.controller.dto.Gpt.GptRequestDto;
import samdasu.recipt.entity.Allergy;
import samdasu.recipt.entity.GptRecipe;
import samdasu.recipt.repository.GptRecipe.GptRecipeRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GptRecipeServiceTest {
    @PersistenceContext
    EntityManager em;
    @Autowired
    GptRecipeService gptRecipeService;
    @Autowired
    GptRecipeRepository gptRecipeRepository;

    @Test
    public void Gpt_레시피_저장() throws Exception {
        //given
        GptRecipe recipe = createRecipe();
        //when
        GptRequestDto gptDto = GptRequestDto.createGptDto(recipe);
        gptRecipeService.gptSave(gptDto);
        //then
        assertThat(gptDto.getGptFoodName()).isEqualTo(recipe.getGptFoodName());
    }

    @Test
    public void Gpt_조회수_탑_10() throws Exception {
        //given

        //when
        System.out.println("############### 조회수 top10 ###############");
        List<GptRecipe> top10ViewCount = gptRecipeService.findTop10ViewCount();
        //then
        for (GptRecipe recipe : top10ViewCount) {
            System.out.println("gptRecipe.getGptViewCount() = " + recipe.getGptViewCount());
        }
    }

    @Test
    public void Gpt_좋아요_탑_10() throws Exception {
        //given

        //when
        System.out.println("############### 좋아요 수 top10 ###############");
        List<GptRecipe> top10LikeCount = gptRecipeService.findTop10LikeCount();
        //then
        for (GptRecipe recipe : top10LikeCount) {
            System.out.println("recipe.getGptLikeCount() = " + recipe.getGptLikeCount());
        }
    }

    @Test
    public void Gpt_레시피_음식이름_단건조회() throws Exception {
        //given

        //when
        GptRequestDto result = gptRecipeService.findByFoodName("계란후라이");

        //then
        assertThat(result.getGptFoodName()).isEqualTo("계란후라이");
    }

    @Test
    public void Gpt_레시피_전체조회() throws Exception {
        //given

        //when
        List<GptRecipe> gptRecipes = gptRecipeRepository.findAll();

        //then
        assertThat(gptRecipes.size()).isEqualTo(15);
    }

    private GptRecipe createRecipe() {
        Allergy allergy = Allergy.createAllergy("갑각류", "새우");
        GptRecipe gptRecipe = GptRecipe.createGptRecipe("만두", "고기피, 만두피", "1.만두 빚기 2.굽기 3.먹기", "음료수랑 먹으면 맛있어요.", 0, 0, 0.0, 0, allergy);
        em.persist(gptRecipe);

        return gptRecipe;
    }
}