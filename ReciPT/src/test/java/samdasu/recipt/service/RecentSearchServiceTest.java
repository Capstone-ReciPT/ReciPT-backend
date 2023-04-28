package samdasu.recipt.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.controller.dto.RecentSearch.DbRecentSearchDto;
import samdasu.recipt.entity.*;
import samdasu.recipt.repository.RecentSearchRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RecentSearchServiceTest {
    @PersistenceContext
    EntityManager em;
    @Autowired
    RecentSearchRepository recentSearchRepository;
    @Autowired
    RecentSearchService recentSearchService;

    @Test
    public void 최근검색어_조회_성공() throws Exception {
        //given
        User user = createUser();
        DbRecipe dbRecipe = createDbRecipe1();

        DbRecentSearchDto recentSearchDto = DbRecentSearchDto.createDbHeartDto(user.getUserId(), dbRecipe.getDbRecipeId());
        //when
        recentSearchService.insertDbSearchInfo(recentSearchDto);
        //then
        assertThat(recentSearchRepository.countRecentSearchBy()).isEqualTo(1);
    }


    @Test
    public void 같은_유저_같은_레시피_최근검색어_저장실패() throws Exception {
        //given
        User user = createUser();
        DbRecipe dbRecipe = createDbRecipe1();

        DbRecentSearchDto recentSearchDto1 = DbRecentSearchDto.createDbHeartDto(user.getUserId(), dbRecipe.getDbRecipeId());

        //when
        recentSearchService.insertDbSearchInfo(recentSearchDto1);
        recentSearchService.insertDbSearchInfo(recentSearchDto1);

        //then
        assertThat(recentSearchRepository.countRecentSearchBy()).isEqualTo(1);
    }
    
    @Test
    public void 같은_유저_다른_레시피_최근검색어_저장() throws Exception {
        //given
        User user = createUser();
        DbRecipe dbRecipe1 = createDbRecipe1();
        DbRecipe dbRecipe2 = createDbRecipe2();

        DbRecentSearchDto recentSearchDto1 = DbRecentSearchDto.createDbHeartDto(user.getUserId(), dbRecipe1.getDbRecipeId());
        DbRecentSearchDto recentSearchDto2 = DbRecentSearchDto.createDbHeartDto(user.getUserId(), dbRecipe2.getDbRecipeId());

        //when
        recentSearchService.insertDbSearchInfo(recentSearchDto1);
        recentSearchService.insertDbSearchInfo(recentSearchDto2);

        //then
        assertThat(recentSearchRepository.countRecentSearchBy()).isEqualTo(2);
    }

    @Test
    public void 최근검색어_10개_초과시_삭제후_저장() throws Exception {
        //given
        User user = createUser();

        for (int i = 100; i < 120; i++) {
            DbRecipe dbRecipe = DbRecipe.createDbRecipe("새우두부계란찜!!" + i, "연두부 75g(3/4모), 칵테일새우 20g(5마리), 달걀 30g(1/2개), 생크림 13g(1큰술), 설탕 5g(1작은술), 무염버터 5g(1작은술), 고명, 시금치 10g(3줄기)",
                    "찌기", "http://www.foodsafetykorea.go.kr/uploadimg/cook/10_00028_1.png", "1. 손질된 새우를 끓는 물에 데쳐 건진다. 2. 연두부, 달걀, 생크림, 설탕에 녹인 무염버터를 믹서에 넣고 간 뒤 새우(1)를 함께 섞어 그릇에 담는다. 3. 시금치를 잘게 다져 혼합물 그릇(2)에 뿌리고 찜기에 넣고 중간 불에서 10분 정도 찐다.",
                    "http://www.foodsafetykorea.go.kr/uploadimg/cook/10_00028_1.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00028_2.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00028_3.png",
                    0, 0L, 0.0, 0, null);
            em.persist(dbRecipe);

            DbRecentSearchDto recentSearchDto1 = DbRecentSearchDto.createDbHeartDto(user.getUserId(), dbRecipe.getDbRecipeId());

            //when
            recentSearchService.insertDbSearchInfo(recentSearchDto1);
        }

        //then
        assertThat(recentSearchRepository.countRecentSearchBy()).isEqualTo(10);
    }

    @Test
    public void 최근_검색어_삭제() throws Exception {
        //given
        User user = createUser();
        DbRecipe dbRecipe = createDbRecipe1();

        createRecentSearch(user, dbRecipe); //최근검색어 저장
        DbRecentSearchDto dbRecentSearchDto = DbRecentSearchDto.createDbHeartDto(user.getUserId(), dbRecipe.getDbRecipeId());

        //when
        recentSearchService.deleteDbSearchInfo(dbRecentSearchDto);

        //then
        long count = recentSearchRepository.count();
        assertThat(count).isEqualTo(0);
    }

    private void createRecentSearch(User user, DbRecipe dbRecipe) {
        RecentSearch recentSearch = RecentSearch.createDbRecentSearch(user, dbRecipe, dbRecipe.getDbFoodName());
        em.persist(recentSearch);
    }


    private User createUser() {
        User user = User.createUser("tester1", "testId", "test1234", "shrimp");
        em.persist(user);

        return user;
    }

    private DbRecipe createDbRecipe1() {
        Allergy allergy = Allergy.createAllergy("갑각류", "새우");
        DbRecipe dbRecipe = DbRecipe.createDbRecipe("새우두부계란찜", "연두부 75g(3/4모), 칵테일새우 20g(5마리), 달걀 30g(1/2개), 생크림 13g(1큰술), 설탕 5g(1작은술), 무염버터 5g(1작은술), 고명, 시금치 10g(3줄기)",
                "찌기", "http://www.foodsafetykorea.go.kr/uploadimg/cook/10_00028_1.png", "1. 손질된 새우를 끓는 물에 데쳐 건진다. 2. 연두부, 달걀, 생크림, 설탕에 녹인 무염버터를 믹서에 넣고 간 뒤 새우(1)를 함께 섞어 그릇에 담는다. 3. 시금치를 잘게 다져 혼합물 그릇(2)에 뿌리고 찜기에 넣고 중간 불에서 10분 정도 찐다.",
                "http://www.foodsafetykorea.go.kr/uploadimg/cook/10_00028_1.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00028_2.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00028_3.png",
                0, 0L, 0.0, 0, allergy);
        em.persist(dbRecipe);
        return dbRecipe;
    }

    private DbRecipe createDbRecipe2() {
        Allergy allergy = Allergy.createAllergy("갑각류", "새우");
        DbRecipe dbRecipe = DbRecipe.createDbRecipe("황태해장국", "황태(채) 15g(10개), 콩나물 30g(1/6봉지), 무 30g(5×3×2cm), 저염된장 10g(2작은술), 물 300ml(1½컵), 청양고추 5g(1/2개), 다진 마늘 2g(1/3작은술)",
                "끓이기", "http://www.foodsafetykorea.go.kr/uploadimg/cook/10_00036_1.png", "3. 황태는 손질하여 물에 헹궈 건져 놓고 콩나물은 다듬어 씻고 청양고추는 어슷썰기 한다. 4. 냄비에 물을 붓고 황태와 무를 넣고 끓인 후 육수에서 물을 건져내고 저염 된장을 푼다. 5. 콩나물, 다진 마늘, 청양고추를 넣고 뚜껑을 덮어 김이 나게 끓여준다.",
                "http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00036_3.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00036_4.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00036_5.png\n",
                1, 1L, 1.0, 1, allergy);
        em.persist(dbRecipe);
        return dbRecipe;
    }

    private GptRecipe createGptRecipe() {
        Allergy allergy = Allergy.createAllergy("갑각류", "새우");
        GptRecipe gptRecipe = GptRecipe.createGptRecipe("만두", "고기피, 만두피", "1.만두 빚기 2.굽기 3.먹기", "음료수랑 먹으면 맛있어요.", 0, 0L, 0.0, 0, allergy);
        em.persist(gptRecipe);

        return gptRecipe;
    }

}