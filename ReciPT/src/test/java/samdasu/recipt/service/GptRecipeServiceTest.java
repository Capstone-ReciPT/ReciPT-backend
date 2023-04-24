package samdasu.recipt.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.controller.dto.Gpt.GptDto;
import samdasu.recipt.entity.Allergy;
import samdasu.recipt.entity.GptRecipe;
import samdasu.recipt.repository.GptRecipe.GptRecipeRepository;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GptRecipeServiceTest {
    private final GptRecipeService gptRecipeService;
    private final GptRecipeRepository gptRecipeRepository;
    private final DataSource dataSource;

    @Autowired
    public GptRecipeServiceTest(GptRecipeService gptRecipeService, GptRecipeRepository gptRecipeRepository, DataSource dataSource) {
        this.gptRecipeService = gptRecipeService;
        this.gptRecipeRepository = gptRecipeRepository;
        this.dataSource = dataSource;
    }


    @Test
    @Rollback(value = false)
    public void 레시피_저장() throws Exception {
        //given
        Allergy allergy = Allergy.createAllergy("갑각류", "새우");

        //when
        for (int i = 0; i < 20; i++) {
            GptRecipe gptRecipe = GptRecipe.createGptRecipe("계란찜" + i, "계란, 파, 당근", "1.손질 2.굽기 3.먹기", "케찹 추가 맛있어요.", 0, i, 0.0, 0, allergy);
            GptDto gptDto = GptDto.createGptDto(gptRecipe);
            gptRecipeService.gptSave(gptDto);
        }
        //then
    }

    @Test
    public void 조회수_탑_10() throws Exception {
        //given
        Allergy allergy = Allergy.createAllergy("갑각류", "새우");
        GptRecipe gptRecipe = null;
        for (int i = 0; i < 20; i++) {
            gptRecipe = GptRecipe.createGptRecipe("계란찜" + i, "계란, 파, 당근", "1.손질 2.굽기 3.먹기", "케찹 추가 맛있어요.", 0, i, 0.0, 0, allergy);
            gptRecipeRepository.save(gptRecipe);
        }

        //when
        System.out.println("############### 조회수 top10 ###############");
        List<GptRecipe> top10ViewCount = gptRecipeService.findTop10ViewCount(gptRecipe);
        //then
        for (GptRecipe recipe : top10ViewCount) {
            System.out.println("gptRecipe.getGptViewCount() = " + recipe.getGptViewCount());
        }
    }

    @Test
    public void Gpt_레시피_ID_단건조회() throws Exception {
        //given
        Allergy allergy = Allergy.createAllergy("갑각류", "새우");
        GptRecipe gptRecipe = GptRecipe.createGptRecipe("계란찜", "계란, 파, 당근", "1.손질 2.굽기 3.먹기", "케찹 추가 맛있어요.", 0, 0, 0.0, 0, allergy);
        gptRecipeRepository.save(gptRecipe);

        //when
        GptDto result = gptRecipeService.findById(gptRecipe.getGptRecipeId());

        //then
        assertThat(result.getGptFoodName()).isEqualTo(gptRecipe.getGptFoodName());
    }

    @Test
    public void Gpt_레시피_음식이름_단건조회() throws Exception {
        //given
        Allergy allergy = Allergy.createAllergy("갑각류", "새우");
        GptRecipe gptRecipe = GptRecipe.createGptRecipe("계란찜", "계란, 파, 당근", "1.손질 2.굽기 3.먹기", "케찹 추가 맛있어요.", 0, 0, 0.0, 0, allergy);
        gptRecipeRepository.save(gptRecipe);

        //when
        GptDto result = gptRecipeService.findByFoodName(gptRecipe.getGptFoodName());

        //then
        assertThat(result.getGptFoodName()).isEqualTo(gptRecipe.getGptFoodName());
    }

    @Test
    public void DB_레시피_전체조회() throws Exception {
        //given
        Allergy allergy = Allergy.createAllergy("갑각류", "새우");
        GptRecipe gptRecipe = null;
        for (int i = 0; i < 20; i++) {
            gptRecipe = GptRecipe.createGptRecipe("계란찜", "계란, 파, 당근", "1.손질 2.굽기 3.먹기", "케찹 추가 맛있어요.", 0, 0, 0.0, 0, allergy);
            gptRecipeRepository.save(gptRecipe);
        }

        //when
        List<GptRecipe> gptRecipes = gptRecipeRepository.findAll();

        //then
        Assertions.assertThat(gptRecipes.size()).isEqualTo(20);
    }

}