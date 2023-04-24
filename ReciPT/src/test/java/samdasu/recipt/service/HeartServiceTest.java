package samdasu.recipt.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.controller.dto.Heart.DbHeartDto;
import samdasu.recipt.entity.Allergy;
import samdasu.recipt.entity.DbRecipe;
import samdasu.recipt.entity.Heart;
import samdasu.recipt.entity.User;
import samdasu.recipt.repository.DbRecipe.DbRecipeRepository;
import samdasu.recipt.repository.GptRecipe.GptRecipeRepository;
import samdasu.recipt.repository.HeartRepository;
import samdasu.recipt.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HeartServiceTest {
    @Autowired
    HeartRepository heartRepository;
    @Autowired
    HeartService heartService;
    @Autowired
    DbRecipeRepository dbRecipeRepository;
    @Autowired
    DbRecipeService dbRecipeService;
    @Autowired
    GptRecipeRepository gptRecipeRepository;
    @Autowired
    GptRecipeService gptRecipeService;
    @Autowired
    UserRepository userRepository;

    @Rollback(value = false)
    @Test
    public void DB_레시피_좋아요() throws Exception {
        //given
        Allergy allergy = Allergy.createAllergy("갑각류", "새우");
        User user = User.createUser("tester1", "testId", "test1234", "shrimp");
        DbRecipe dbRecipe = DbRecipe.createDbRecipe("새우두부계란찜", "연두부 75g(3/4모), 칵테일새우 20g(5마리), 달걀 30g(1/2개), 생크림 13g(1큰술), 설탕 5g(1작은술), 무염버터 5g(1작은술), 고명, 시금치 10g(3줄기)",
                "찌기", "http://www.foodsafetykorea.go.kr/uploadimg/cook/10_00028_1.png", "1. 손질된 새우를 끓는 물에 데쳐 건진다. 2. 연두부, 달걀, 생크림, 설탕에 녹인 무염버터를 믹서에 넣고 간 뒤 새우(1)를 함께 섞어 그릇에 담는다. 3. 시금치를 잘게 다져 혼합물 그릇(2)에 뿌리고 찜기에 넣고 중간 불에서 10분 정도 찐다.",
                "http://www.foodsafetykorea.go.kr/uploadimg/cook/10_00028_1.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00028_2.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00028_3.png",
                0, 0, 0.0, 0, allergy);
        userRepository.save(user);
        dbRecipeRepository.save(dbRecipe);

        DbHeartDto dbHeart = DbHeartDto.createDbHeartDto(user.getUserId(), dbRecipe.getDbRecipeId());

        //when
        Long resultId = heartService.insertDbHeart(dbHeart);
        System.out.println("resultId = " + resultId);
        Heart resultHeart = heartRepository.findById(resultId).get();

        //then
        assertThat(resultHeart.getDbRecipe().getDbLikeCount()).isEqualTo(1);
    }

    @Test
    public void DB_레시피_좋아요_취소() throws Exception {
        //given
        Allergy allergy = Allergy.createAllergy("갑각류", "새우");
        User user = User.createUser("tester1", "testId", "test1234", "shrimp");
        DbRecipe dbRecipe = DbRecipe.createDbRecipe("새우두부계란찜", "연두부 75g(3/4모), 칵테일새우 20g(5마리), 달걀 30g(1/2개), 생크림 13g(1큰술), 설탕 5g(1작은술), 무염버터 5g(1작은술), 고명, 시금치 10g(3줄기)",
                "찌기", "http://www.foodsafetykorea.go.kr/uploadimg/cook/10_00028_1.png", "1. 손질된 새우를 끓는 물에 데쳐 건진다. 2. 연두부, 달걀, 생크림, 설탕에 녹인 무염버터를 믹서에 넣고 간 뒤 새우(1)를 함께 섞어 그릇에 담는다. 3. 시금치를 잘게 다져 혼합물 그릇(2)에 뿌리고 찜기에 넣고 중간 불에서 10분 정도 찐다.",
                "http://www.foodsafetykorea.go.kr/uploadimg/cook/10_00028_1.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00028_2.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00028_3.png",
                1, 0, 0.0, 0, allergy);
        userRepository.save(user);
        dbRecipeRepository.save(dbRecipe);

        Heart dbHeart = Heart.createDbHeart(user, dbRecipe);
        heartRepository.save(dbHeart);
        DbHeartDto dbHeartDto = DbHeartDto.createDbHeartDto(user.getUserId(), dbRecipe.getDbRecipeId());
        /**
         * 파라미터 값: dto 필요
         * but dto를 .save() 시킬순 없음... -> save되지 않으니 heart를 찾을수 없다! 에러뜨는 듯...
         */

        //when
        heartService.deleteDbHeart(dbHeartDto);
        DbRecipe findDbRecipe = dbRecipeRepository.findById(dbHeart.getDbRecipe().getDbRecipeId()).get();

        //then
        assertThat(findDbRecipe.getDbLikeCount()).isEqualTo(0);
    }
}