package samdasu.recipt.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.controller.dto.DbDto;
import samdasu.recipt.entity.Allergy;
import samdasu.recipt.entity.DbRecipe;
import samdasu.recipt.repository.DbRecipe.DbRecipeRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DbRecipeServiceTest {
    private final DbRecipeRepository dbRecipeRepository;
    private final DbRecipeService dbRecipeService;
    private final DataSource dataSource;

    @Autowired
    public DbRecipeServiceTest(DbRecipeRepository dbRecipeRepository, DbRecipeService dbRecipeService, DataSource dataSource) {
        this.dbRecipeRepository = dbRecipeRepository;
        this.dbRecipeService = dbRecipeService;
        this.dataSource = dataSource;
    }

    @BeforeAll
    public void init() {
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("/db/h2/data.sql"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
                    0, i, allergy);
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
                0, 0, allergy);
        dbRecipeRepository.save(dbRecipe);

        //when
        DbDto result = dbRecipeService.findById(dbRecipe.getDbRecipeId());

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
                0, 0, allergy);
        dbRecipeRepository.save(dbRecipe);

        //when
        DbDto result = dbRecipeService.findByFoodName(dbRecipe.getDbFoodName());

        //then
        assertThat(result.getDbFoodName()).isEqualTo(dbRecipe.getDbFoodName());
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
                    i, 0, allergy);
            dbRecipeRepository.save(dbRecipe);
        }

        //when
        List<DbRecipe> dbRecipes = dbRecipeService.findAll();

        //then
        Assertions.assertThat(dbRecipes.size()).isEqualTo(20);
    }
}