package samdasu.recipt.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.controller.dto.Heart.DbHeartDto;
import samdasu.recipt.controller.dto.Heart.GptHeartDto;
import samdasu.recipt.entity.*;
import samdasu.recipt.repository.HeartRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HeartServiceTest {
    @PersistenceContext
    EntityManager em;
    @Autowired
    HeartRepository heartRepository;
    @Autowired
    HeartService heartService;

    @Test
    public void DB_레시피_좋아요() throws Exception {
        //given
        DbRecipe dbRecipe = createDbRecipe();
        User user = createUser();

        DbHeartDto dbHeart = DbHeartDto.createDbHeartDto(user.getUserId(), dbRecipe.getDbRecipeId());

        //when
        heartService.insertDbHeart(dbHeart);

        //then
        assertThat(heartRepository.countHeartBy()).isEqualTo(1);
    }

    @Test
    public void DB_레시피_좋아요_취소() throws Exception {
        //given
        DbRecipe dbRecipe = createDbRecipe();
        User user = createUser();

        Heart heart = Heart.createDbHeart(user, dbRecipe);
        heartRepository.save(heart);
        //when
        DbHeartDto dbHeart = DbHeartDto.createDbHeartDto(user.getUserId(), dbRecipe.getDbRecipeId());
        heartService.deleteDbHeart(dbHeart);

        //then
        assertThat(heartRepository.countHeartBy()).isEqualTo(0);
    }

    @Test
    public void Gpt_레시피_좋아요() throws Exception {
        //given
        GptRecipe gptRecipe = createGptRecipe();
        User user = createUser();

        GptHeartDto gptHeart = GptHeartDto.createGptHeartDto(user.getUserId(), gptRecipe.getGptRecipeId());

        //when
        heartService.insertGptHeart(gptHeart);

        //then
        assertThat(heartRepository.countHeartBy()).isEqualTo(1);
    }

    @Test
    public void Gpt_레시피_좋아요_취소() throws Exception {
        //given
        GptRecipe gptRecipe = createGptRecipe();
        User user = createUser();

        Heart heart = Heart.createGptHeart(user, gptRecipe);
        heartRepository.save(heart);
        //when
        GptHeartDto gptHeart = GptHeartDto.createGptHeartDto(user.getUserId(), gptRecipe.getGptRecipeId());
        heartService.deleteGptHeart(gptHeart);

        //then
        assertThat(heartRepository.countHeartBy()).isEqualTo(0);
    }

    private User createUser() {
        User user = User.createUser("tester1", "testId", "test1234", "shrimp");
        em.persist(user);

        return user;
    }

    private DbRecipe createDbRecipe() {
        Allergy allergy = Allergy.createAllergy("갑각류", "새우");
        DbRecipe dbRecipe = DbRecipe.createDbRecipe("새우두부계란찜", "연두부 75g(3/4모), 칵테일새우 20g(5마리), 달걀 30g(1/2개), 생크림 13g(1큰술), 설탕 5g(1작은술), 무염버터 5g(1작은술), 고명, 시금치 10g(3줄기)",
                "찌기", "http://www.foodsafetykorea.go.kr/uploadimg/cook/10_00028_1.png", "1. 손질된 새우를 끓는 물에 데쳐 건진다. 2. 연두부, 달걀, 생크림, 설탕에 녹인 무염버터를 믹서에 넣고 간 뒤 새우(1)를 함께 섞어 그릇에 담는다. 3. 시금치를 잘게 다져 혼합물 그릇(2)에 뿌리고 찜기에 넣고 중간 불에서 10분 정도 찐다.",
                "http://www.foodsafetykorea.go.kr/uploadimg/cook/10_00028_1.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00028_2.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00028_3.png",
                0, 0, 0.0, 0, allergy);
        em.persist(dbRecipe);

        return dbRecipe;
    }

    private GptRecipe createGptRecipe() {
        Allergy allergy = Allergy.createAllergy("갑각류", "새우");
        GptRecipe gptRecipe = GptRecipe.createGptRecipe("만두", "고기피, 만두피", "1.만두 빚기 2.굽기 3.먹기", "음료수랑 먹으면 맛있어요.", 0, 0, 0.0, 0, allergy);
        em.persist(gptRecipe);

        return gptRecipe;
    }
}