package samdasu.recipt.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.entity.Allergy;
import samdasu.recipt.entity.DbRecipe;
import samdasu.recipt.entity.GptRecipe;
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


//    @Test
//    @Rollback(value = false)
//    public void 레시피_저장() throws Exception {
//        //given
//        Allergy allergy = Allergy.createAllergy("갑각류", "새우");
//        for (int i = 0; i < 20; i++) {
//            DbRecipe dbRecipe = DbRecipe.createDbRecipe("새우두부계란찜" + i, "연두부 75g(3/4모), 칵테일새우 20g(5마리), 달걀 30g(1/2개), 생크림 13g(1큰술), 설탕 5g(1작은술), 무염버터 5g(1작은술), 고명, 시금치 10g(3줄기)",
//                    "찌기", "http://www.foodsafetykorea.go.kr/uploadimg/cook/10_00028_1.png", "1. 손질된 새우를 끓는 물에 데쳐 건진다. 2. 연두부, 달걀, 생크림, 설탕에 녹인 무염버터를 믹서에 넣고 간 뒤 새우(1)를 함께 섞어 그릇에 담는다. 3. 시금치를 잘게 다져 혼합물 그릇(2)에 뿌리고 찜기에 넣고 중간 불에서 10분 정도 찐다.",
//                    "http://www.foodsafetykorea.go.kr/uploadimg/cook/10_00028_1.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00028_2.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00028_3.png",
//                    i, allergy);
//        }
//        //when
//
//        //then
//    }

    @Test
    public void 조회수_탑_10() throws Exception {
        //given
        Allergy allergy = Allergy.createAllergy("갑각류", "새우");
        DbRecipe dbRecipe = null;
        for (int i = 0; i < 20; i++) {
            dbRecipe = DbRecipe.createDbRecipe("새우두부계란찜" + i, "연두부 75g(3/4모), 칵테일새우 20g(5마리), 달걀 30g(1/2개), 생크림 13g(1큰술), 설탕 5g(1작은술), 무염버터 5g(1작은술), 고명, 시금치 10g(3줄기)",
                    "찌기", "http://www.foodsafetykorea.go.kr/uploadimg/cook/10_00028_1.png", "1. 손질된 새우를 끓는 물에 데쳐 건진다. 2. 연두부, 달걀, 생크림, 설탕에 녹인 무염버터를 믹서에 넣고 간 뒤 새우(1)를 함께 섞어 그릇에 담는다. 3. 시금치를 잘게 다져 혼합물 그릇(2)에 뿌리고 찜기에 넣고 중간 불에서 10분 정도 찐다.",
                    "http://www.foodsafetykorea.go.kr/uploadimg/cook/10_00028_1.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00028_2.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00028_3.png",
                    0, i, 0.0, 0, allergy);
            dbRecipeRepository.save(dbRecipe);
        }

        //when
        System.out.println("############### 조회수 top10 ###############");
        List<DbRecipe> top10ViewCount = dbRecipeService.findTop10ViewCount(dbRecipe);
        //then
        for (DbRecipe recipe : top10ViewCount) {
            System.out.println("dbRecipe.getDbViewCount() = " + recipe.getDbViewCount());
        }
    }

    @Test
    public void DB_레시피_ID_단건조회() throws Exception {
        //given
        Allergy allergy = Allergy.createAllergy("갑각류", "새우");
        DbRecipe dbRecipe = DbRecipe.createDbRecipe("새우두부계란찜", "연두부 75g(3/4모), 칵테일새우 20g(5마리), 달걀 30g(1/2개), 생크림 13g(1큰술), 설탕 5g(1작은술), 무염버터 5g(1작은술), 고명, 시금치 10g(3줄기)",
                "찌기", "http://www.foodsafetykorea.go.kr/uploadimg/cook/10_00028_1.png", "1. 손질된 새우를 끓는 물에 데쳐 건진다. 2. 연두부, 달걀, 생크림, 설탕에 녹인 무염버터를 믹서에 넣고 간 뒤 새우(1)를 함께 섞어 그릇에 담는다. 3. 시금치를 잘게 다져 혼합물 그릇(2)에 뿌리고 찜기에 넣고 중간 불에서 10분 정도 찐다.",
                "http://www.foodsafetykorea.go.kr/uploadimg/cook/10_00028_1.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00028_2.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00028_3.png",
                0, 0, 0.0, 0, allergy);
        dbRecipeRepository.save(dbRecipe);

        //when
        DbRecipe result = dbRecipeService.findById(dbRecipe.getDbRecipeId());

        //then
        assertThat(result.getDbFoodName()).isEqualTo(dbRecipe.getDbFoodName());
    }

    @Test
    public void DB_레시피_음식이름_단건조회() throws Exception {
        //given
        Allergy allergy = Allergy.createAllergy("갑각류", "새우");
        DbRecipe dbRecipe = DbRecipe.createDbRecipe("새우두부계란찜", "연두부 75g(3/4모), 칵테일새우 20g(5마리), 달걀 30g(1/2개), 생크림 13g(1큰술), 설탕 5g(1작은술), 무염버터 5g(1작은술), 고명, 시금치 10g(3줄기)",
                "찌기", "http://www.foodsafetykorea.go.kr/uploadimg/cook/10_00028_1.png", "1. 손질된 새우를 끓는 물에 데쳐 건진다. 2. 연두부, 달걀, 생크림, 설탕에 녹인 무염버터를 믹서에 넣고 간 뒤 새우(1)를 함께 섞어 그릇에 담는다. 3. 시금치를 잘게 다져 혼합물 그릇(2)에 뿌리고 찜기에 넣고 중간 불에서 10분 정도 찐다.",
                "http://www.foodsafetykorea.go.kr/uploadimg/cook/10_00028_1.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00028_2.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00028_3.png",
                0, 0, 0.0, 0, allergy);
        dbRecipeRepository.save(dbRecipe);

        //when
        DbRecipe result = dbRecipeService.findByFoodName(dbRecipe.getDbFoodName());

        //then
        assertThat(result.getDbFoodName()).isEqualTo(dbRecipe.getDbFoodName());
    }

    @Test
    public void DB_레시피_음식이름포함_조회o() throws Exception {
        //given
        DbRecipe dbRecipe = DbRecipe.createDbRecipe("방울토마토 소박이", "방울토마토 150g(5개), 양파 10g(3×1cm), 부추 10g(5줄기), 고춧가루 4g(1작은술), 멸치액젓 3g(2/3작은술), 다진 마늘 2.5g(1/2쪽), 매실액 2g(1/3작은술), 설탕 2g(1/3작은술), 물 2ml(1/3작은술), 통깨 약간",
                "기타", " http://www.foodsafetykorea.go.kr/uploadimg/cook/10_00031_2.png", "1. 물기를 빼고 2cm 정도의 크기로 썰은 부추와 양파를 양념장에 섞어 양념속을 만든다. 2. 깨끗이 씻은 방울토마토는 꼭지를 떼고 윗부분에 칼로 십자모양으로 칼집을 낸다. 3. 칼집을 낸 방울토마토에 양념속을 사이사이에 넣어 버무린다.",
                "http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00031_1.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00031_4.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00031_5.png",
                0, 0, 0.0, 0, null);
        dbRecipeRepository.save(dbRecipe);
        for (int i = 0; i < 5; i++) {
            Allergy allergy = Allergy.createAllergy("갑각류", "새우");
            dbRecipe = DbRecipe.createDbRecipe("새우두부계란찜" + i, "연두부 75g(3/4모), 칵테일새우 20g(5마리), 달걀 30g(1/2개), 생크림 13g(1큰술), 설탕 5g(1작은술), 무염버터 5g(1작은술), 고명, 시금치 10g(3줄기)",
                    "찌기", "http://www.foodsafetykorea.go.kr/uploadimg/cook/10_00028_1.png", "1. 손질된 새우를 끓는 물에 데쳐 건진다. 2. 연두부, 달걀, 생크림, 설탕에 녹인 무염버터를 믹서에 넣고 간 뒤 새우(1)를 함께 섞어 그릇에 담는다. 3. 시금치를 잘게 다져 혼합물 그릇(2)에 뿌리고 찜기에 넣고 중간 불에서 10분 정도 찐다.",
                    "http://www.foodsafetykorea.go.kr/uploadimg/cook/10_00028_1.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00028_2.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00028_3.png",
                    i, 0, 0.0, 0, allergy);
            dbRecipeRepository.save(dbRecipe);
        }
        //when
        List<DbRecipe> dbRecipes = dbRecipeService.AllDbRecipeView(dbRecipe, "계란찜");

        //then
        Assertions.assertThat(dbRecipes.size()).isEqualTo(5);
    }

    @Test
    public void DB_레시피_음식이름포함_조회x() throws Exception {
        //given
        DbRecipe dbRecipe = DbRecipe.createDbRecipe("방울토마토 소박이", "방울토마토 150g(5개), 양파 10g(3×1cm), 부추 10g(5줄기), 고춧가루 4g(1작은술), 멸치액젓 3g(2/3작은술), 다진 마늘 2.5g(1/2쪽), 매실액 2g(1/3작은술), 설탕 2g(1/3작은술), 물 2ml(1/3작은술), 통깨 약간",
                "기타", " http://www.foodsafetykorea.go.kr/uploadimg/cook/10_00031_2.png", "1. 물기를 빼고 2cm 정도의 크기로 썰은 부추와 양파를 양념장에 섞어 양념속을 만든다. 2. 깨끗이 씻은 방울토마토는 꼭지를 떼고 윗부분에 칼로 십자모양으로 칼집을 낸다. 3. 칼집을 낸 방울토마토에 양념속을 사이사이에 넣어 버무린다.",
                "http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00031_1.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00031_4.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00031_5.png",
                0, 0, 0.0, 0, null);
        dbRecipeRepository.save(dbRecipe);
        for (int i = 0; i < 5; i++) {
            Allergy allergy = Allergy.createAllergy("갑각류", "새우");
            dbRecipe = DbRecipe.createDbRecipe("새우두부계란찜" + i, "연두부 75g(3/4모), 칵테일새우 20g(5마리), 달걀 30g(1/2개), 생크림 13g(1큰술), 설탕 5g(1작은술), 무염버터 5g(1작은술), 고명, 시금치 10g(3줄기)",
                    "찌기", "http://www.foodsafetykorea.go.kr/uploadimg/cook/10_00028_1.png", "1. 손질된 새우를 끓는 물에 데쳐 건진다. 2. 연두부, 달걀, 생크림, 설탕에 녹인 무염버터를 믹서에 넣고 간 뒤 새우(1)를 함께 섞어 그릇에 담는다. 3. 시금치를 잘게 다져 혼합물 그릇(2)에 뿌리고 찜기에 넣고 중간 불에서 10분 정도 찐다.",
                    "http://www.foodsafetykorea.go.kr/uploadimg/cook/10_00028_1.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00028_2.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00028_3.png",
                    i, 0, 0.0, 0, allergy);
            dbRecipeRepository.save(dbRecipe);
        }
        //when
        List<DbRecipe> dbRecipes = dbRecipeService.AllDbRecipeView(dbRecipe, "고기");

        //then
        Assertions.assertThat(dbRecipes.size()).isEqualTo(0);
    }

    @Test
    public void DB_레시피_전체조회() throws Exception {
        //given
        Allergy allergy = Allergy.createAllergy("갑각류", "새우");
        DbRecipe dbRecipe = null;
        for (int i = 0; i < 20; i++) {
            dbRecipe = DbRecipe.createDbRecipe("새우두부계란찜" + i, "연두부 75g(3/4모), 칵테일새우 20g(5마리), 달걀 30g(1/2개), 생크림 13g(1큰술), 설탕 5g(1작은술), 무염버터 5g(1작은술), 고명, 시금치 10g(3줄기)",
                    "찌기", "http://www.foodsafetykorea.go.kr/uploadimg/cook/10_00028_1.png", "1. 손질된 새우를 끓는 물에 데쳐 건진다. 2. 연두부, 달걀, 생크림, 설탕에 녹인 무염버터를 믹서에 넣고 간 뒤 새우(1)를 함께 섞어 그릇에 담는다. 3. 시금치를 잘게 다져 혼합물 그릇(2)에 뿌리고 찜기에 넣고 중간 불에서 10분 정도 찐다.",
                    "http://www.foodsafetykorea.go.kr/uploadimg/cook/10_00028_1.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00028_2.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00028_3.png",
                    i, 0, 0.0, 0, allergy);
            dbRecipeRepository.save(dbRecipe);
        }

        //when
        List<DbRecipe> dbRecipes = dbRecipeService.findAll();

        //then
        Assertions.assertThat(dbRecipes.size()).isEqualTo(20);
    }

    @PersistenceContext
    EntityManager em;

    @Test
    @Rollback(value = false)
    public void DB_GPT_조회수_탑10() throws Exception {
        //given
        Allergy allergy = Allergy.createAllergy("갑각류", "새우");
        for (int i = 0; i < 20; i++) {
            DbRecipe dbRecipe = DbRecipe.createDbRecipe("새우두부계란찜" + i, "연두부 75g(3/4모), 칵테일새우 20g(5마리), 달걀 30g(1/2개), 생크림 13g(1큰술), 설탕 5g(1작은술), 무염버터 5g(1작은술), 고명, 시금치 10g(3줄기)",
                    "찌기", "http://www.foodsafetykorea.go.kr/uploadimg/cook/10_00028_1.png", "1. 손질된 새우를 끓는 물에 데쳐 건진다. 2. 연두부, 달걀, 생크림, 설탕에 녹인 무염버터를 믹서에 넣고 간 뒤 새우(1)를 함께 섞어 그릇에 담는다. 3. 시금치를 잘게 다져 혼합물 그릇(2)에 뿌리고 찜기에 넣고 중간 불에서 10분 정도 찐다.",
                    "http://www.foodsafetykorea.go.kr/uploadimg/cook/10_00028_1.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00028_2.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00028_3.png",
                    i, 0, 0.0, 0, allergy);

            GptRecipe gptRecipe = GptRecipe.createGptRecipe("계란찜" + i, "계란, 파, 당근", "1.손질 2.굽기 3.먹기", "케찹 추가 맛있어요.", 0, i, 0.0, 0, allergy);
            em.persist(dbRecipe);
            em.persist(gptRecipe);
        }
        em.flush();
        em.clear();

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