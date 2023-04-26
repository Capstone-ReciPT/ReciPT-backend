package samdasu.recipt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.entity.*;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
        initService.dbInit3();

        initService.gptInit1();
        initService.gptInit2();
        initService.gptInit3();

        initService.reviewInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;

        public void dbInit1() {
            User user = User.createUser("testerA", "testA", "A1234", "shrimp");
            em.persist(user);

            Allergy allergy = Allergy.createAllergy("갑각류", "새우");

            DbRecipe dbRecipe1 = DbRecipe.createDbRecipe("새우두부계란찜", "연두부 75g(3/4모), 칵테일새우 20g(5마리), 달걀 30g(1/2개), 생크림 13g(1큰술), 설탕 5g(1작은술), 무염버터 5g(1작은술), 고명, 시금치 10g(3줄기)",
                    "찌기", "http://www.foodsafetykorea.go.kr/uploadimg/cook/10_00028_1.png", "1. 손질된 새우를 끓는 물에 데쳐 건진다. 2. 연두부, 달걀, 생크림, 설탕에 녹인 무염버터를 믹서에 넣고 간 뒤 새우(1)를 함께 섞어 그릇에 담는다. 3. 시금치를 잘게 다져 혼합물 그릇(2)에 뿌리고 찜기에 넣고 중간 불에서 10분 정도 찐다.",
                    "http://www.foodsafetykorea.go.kr/uploadimg/cook/10_00028_1.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00028_2.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00028_3.png",
                    0, 0L, 0.0, 0, allergy);
            em.persist(dbRecipe1);

            DbRecipe dbRecipe2 = DbRecipe.createDbRecipe("부추 콩가루 찜", "조선부추 50g, 날콩가루 7g(1⅓작은술), 저염간장 3g(2/3작은술), 다진 대파 5g(1작은술), 다진 마늘 2g(1/2쪽), 고춧가루 2g(1/3작은술), 요리당 2g(1/3작은술), 참기름 2g(1/3작은술), 참깨 약간",
                    "찌기", "http://www.foodsafetykorea.go.kr/uploadimg/cook/10_00029_1.png", "1. 부추는 깨끗이 씻어 물기를 제거하고, 5cm 길이로 썰고 부추에 날콩가루를 넣고 고루 섞이도록 버무린다. 2. 찜기에 면보를 깔고 부추를 넣은 후 김이 오르게 쪄서 파랗게 익힌다. 3. 저염간장에 다진 대파, 다진 마늘, 고춧가루, 요리당 , 참기름, 참깨를 섞어 양념장을 만들고 찐 부추는 그릇에 담아낸다.",
                    "http://www.foodsafetykorea.go.kr/uploadimg/20230206/20230206054820_1675673300714.jpg, http://www.foodsafetykorea.go.kr/uploadimg/20230206/20230206054834_1675673314720.jpg, http://www.foodsafetykorea.go.kr/uploadimg/20230206/20230206054908_1675673348152.jpg",
                    0, 0L, 0.0, 0, allergy);
            em.persist(dbRecipe2);

            Review review1 = Review.createDbReview("새우두부계란찜 후기", "맛있는 계란찜", 0L, 0, user, dbRecipe1);
            em.persist(review1);

            Review review2 = Review.createDbReview("부추 콩가루 찜 후기", "이게 무슨 요리지?", 0L, 0, user, dbRecipe2);
            em.persist(review2);
        }

        public void dbInit2() {
            User user = User.createUser("testerB", "testB", "B1234", "milk");
            em.persist(user);

            Allergy allergy = Allergy.createAllergy("갑각류", "새우");

            DbRecipe dbRecipe1 = DbRecipe.createDbRecipe("사과 새우 북엇국", "북어채 25g(15개), 새우 10g(3마리), 사과 30g(1/5개), 양파 40g(1/4개), 표고버섯 20g(2장), 물 300ml(1½컵)",
                    "끓이기", "http://www.foodsafetykorea.go.kr/uploadimg/cook/10_00033_1.png", "2. 북어채를 잘게 손으로 찢어 찬물에 헹구어 준비한 후 양파, 표고버섯, 사과는 채 썰고 새우는 등쪽의 두세 마디에 꼬챙이를 넣어 내장을 뺀 후 헹군다. 3. 찬물에 북어채, 새우, 표고버섯, 양파를 넣고 20분 정도 끓인 후 사과를 넣어 북어의 씁쓸한 맛을 없앤다. 5. 그릇에 담아낸다.",
                    "http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00033_2.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00033_3.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00033_5.png",
                    0, 0L, 0.0, 0, allergy);
            em.persist(dbRecipe1);

            DbRecipe dbRecipe2 = DbRecipe.createDbRecipe("황태해장국", "황태(채) 15g(10개), 콩나물 30g(1/6봉지), 무 30g(5×3×2cm), 저염된장 10g(2작은술), 물 300ml(1½컵), 청양고추 5g(1/2개), 다진 마늘 2g(1/3작은술)",
                    "끓이기", "http://www.foodsafetykorea.go.kr/uploadimg/cook/10_00036_1.png", "3. 황태는 손질하여 물에 헹궈 건져 놓고 콩나물은 다듬어 씻고 청양고추는 어슷썰기 한다. 4. 냄비에 물을 붓고 황태와 무를 넣고 끓인 후 육수에서 물을 건져내고 저염 된장을 푼다. 5. 콩나물, 다진 마늘, 청양고추를 넣고 뚜껑을 덮어 김이 나게 끓여준다.",
                    "http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00036_3.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00036_4.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00036_5.png\n",
                    0, 0L, 0.0, 0, allergy);
            em.persist(dbRecipe2);

            Review review1 = Review.createDbReview("사과 새우 북엇국 후기", "얼큰하다", 0L, 0, user, dbRecipe1);
            em.persist(review1);

            Review review2 = Review.createDbReview("황태해장국 후기", "해장에 딱이지", 0L, 0, user, dbRecipe2);
            em.persist(review2);
        }

        public void dbInit3() {
            Allergy allergy = Allergy.createAllergy("갑각류", "새우");
            for (int i = 0; i < 10; i++) {
                DbRecipe dbRecipe = DbRecipe.createDbRecipe("새우두부계란찜" + i, "연두부 75g(3/4모), 칵테일새우 20g(5마리), 달걀 30g(1/2개), 생크림 13g(1큰술), 설탕 5g(1작은술), 무염버터 5g(1작은술), 고명, 시금치 10g(3줄기)",
                        "찌기", "http://www.foodsafetykorea.go.kr/uploadimg/cook/10_00028_1.png", "1. 손질된 새우를 끓는 물에 데쳐 건진다. 2. 연두부, 달걀, 생크림, 설탕에 녹인 무염버터를 믹서에 넣고 간 뒤 새우(1)를 함께 섞어 그릇에 담는다. 3. 시금치를 잘게 다져 혼합물 그릇(2)에 뿌리고 찜기에 넣고 중간 불에서 10분 정도 찐다.",
                        "http://www.foodsafetykorea.go.kr/uploadimg/cook/10_00028_1.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00028_2.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00028_3.png",
                        0, (long) i, 0.0, 0, allergy);
                em.persist(dbRecipe);
            }
        }

        public void gptInit1() {
            User user = User.createUser("testerC", "testC", "C1234", "bean");
            em.persist(user);

            Allergy allergy = Allergy.createAllergy("갑각류", "새우");

            GptRecipe gptRecipe1 = GptRecipe.createGptRecipe("계란찜", "계란, 파, 당근", "1.손질 2.굽기 3.먹기", "케찹 추가 맛있어요.", 0, 0L, 0.0, 0, allergy);
            em.persist(gptRecipe1);

            GptRecipe gptRecipe2 = GptRecipe.createGptRecipe("계란후라이", "계란", "1.계란 톡 2.굽기", "케찹이랑 먹으면 맛있어요.", 0, 0L, 0.0, 0, allergy);
            em.persist(gptRecipe2);

            Review review1 = Review.createGptReview("계란찜 후기", "밥도둑이네", 0L, 0, user, gptRecipe1);
            em.persist(review1);

            Review review2 = Review.createGptReview("계란후라이 후기", "아침에는 계란 후라이", 0L, 0, user, gptRecipe2);
            em.persist(review2);
        }

        public void gptInit2() {
            User user = User.createUser("testerD", "testD", "D1234", "peanut");
            em.persist(user);

            Allergy allergy = Allergy.createAllergy("갑각류", "새우");

            GptRecipe gptRecipe1 = GptRecipe.createGptRecipe("오므라이스", "계란, 밥, 당근, 양파", "1.채소 썰기 2.굽기 3.플레이팅", "김이랑 먹으면 맛있어요.", 0, 0L, 0.0, 0, allergy);
            em.persist(gptRecipe1);

            GptRecipe gptRecipe2 = GptRecipe.createGptRecipe("오이무침", "오이, 쪽파, 양파, 고춧가루, 참기름, 깨", "1.채소 손질 2.버무리기 3.플레이팅", "밥이랑 먹으면 맛있어요.", 0, 0L, 0.0, 0, allergy);
            em.persist(gptRecipe2);

            Review review1 = Review.createGptReview("오므라이스 후기", "어린아이도 좋아해요", 0L, 0, user, gptRecipe1);
            em.persist(review1);

            Review review2 = Review.createGptReview("오이무침 후기", "아삭아삭 오이무침", 0L, 0, user, gptRecipe2);
            em.persist(review2);
        }

        public void gptInit3() {
            Allergy allergy = Allergy.createAllergy("갑각류", "새우");

            for (int i = 0; i < 10; i++) {
                GptRecipe gptRecipe = GptRecipe.createGptRecipe("계란찜" + i, "계란, 파, 당근", "1.손질 2.굽기 3.먹기", "케찹 추가 맛있어요.", 0, (long) i, 0.0, 0, allergy);
                em.persist(gptRecipe);
            }
        }

        public void reviewInit() {
            User user = User.createUser("testerD", "testD", "D1234", "peanut");
            em.persist(user);

            Allergy allergy = Allergy.createAllergy("갑각류", "새우");

            GptRecipe gptRecipe = GptRecipe.createGptRecipe("계란찜", "계란, 파, 당근", "1.손질 2.굽기 3.먹기", "케찹 추가 맛있어요.", 0, 0L, 0.0, 0, allergy);
            em.persist(gptRecipe);

            for (int i = 0; i < 10; i++) {
                Review review1 = Review.createGptReview("계란찜 후기", "밥도둑이네", 0L, 0, user, gptRecipe);
                em.persist(review1);

            }
        }
    }


}
