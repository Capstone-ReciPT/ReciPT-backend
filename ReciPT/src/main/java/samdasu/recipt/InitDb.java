package samdasu.recipt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
        initService.recipeInit1();
        initService.recipeInit2();

        initService.registerRecipeInit1();
        initService.registerRecipeInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;
        private final BCryptPasswordEncoder passwordEncoder;

        public void recipeInit1() {
            Profile profile = Profile.createProfile("profile", "jpg", null);
            em.persist(profile);

            User user = User.createUser("testerA", "testA", passwordEncoder.encode("A1234"), 10, profile);
            em.persist(user);

            Recipe recipe1 = Recipe.createRecipe("된장 부대찌개", "다시마 1g, 두부 10g, 떡국 떡 10g, 스팸(마일드) 10g, 다진 마늘 5g, 무 20g, 김치 15g, 소시지 10g(1/2개), 우민찌 5g(1작은술), 양파 5g, 저염된장 15g(1큰술), 베이컨 5g, 대파 5g, 청양고추 5g, 홍고추 1g", "찌개", "http://www.foodsafetykorea.go.kr/uploadimg/cook/10_00138_1.png",
                    "1. 김치, 베이컨, 스팸, 소시지, 양파, 두부, 무는 두께 0.5cm로 썬다. 2. 다시마와 물을 끓여 다시마물을 만든다. 3. 냄비에 소시지, 베이컨, 두부, 스팸, 무, 우민찌, 김치, 다시마물 300g을 넣어 끓인 후 저염된장, 양파, 대파, 다진 마늘, 떡국 떡을 넣고 재료가 다 익으면 홍고추와 청양고추를 넣어 완성한다.", "http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00138_1.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00138_2.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00138_3.png",
                    30L, 10, 2.0, 0);
            em.persist(recipe1);

            Recipe recipe2 = Recipe.createRecipe("오렌지 당근 주스", "당근 100g(1/2개), 오렌지 100g(1/2개)", "주스", "http://www.foodsafetykorea.go.kr/uploadimg/cook/10_00108_1.png",
                    "1. 오렌지는 깨끗이 씻어 껍질을 벗긴다. 2. 당근은 깨끗이 씻어 작은 토막으로 썬다. 3. 당근, 오렌지, 물(50ml)을 믹서에 곱게 간다. 4. 믹서에 갈아낸 주스는 고운 체로 거른다. 5. 주스를 살얼음이 생길 만큼 시원하게 냉동한다. 6. 컵에 담아 마무리한다.", "http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00108_1.jpg, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00108_2.jpg, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00108_3.jpg, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00108_4.jpg, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00108_5.jpg",
                    50L, 50, 4.0, 1);
            em.persist(recipe2);

            Review review1 = Review.createRecipeReview("된장 부대찌개 후기", 2, 2.0, user, recipe1);
            em.persist(review1);

            Review review2 = Review.createRecipeReview("오렌지 당근 주스 후기", 4, 4.0, user, recipe2);
            em.persist(review2);

            Heart recipeHeart1 = Heart.createRecipeHeart(user, recipe1);
            em.persist(recipeHeart1);

            Heart recipeHeart2 = Heart.createRecipeHeart(user, recipe2);
            em.persist(recipeHeart2);
        }

        public void recipeInit2() {
            Profile profile = Profile.createProfile("file", "png", null);
            em.persist(profile);

            User user = User.createUser("testerB", "testB", passwordEncoder.encode("B1234"), 20, profile);
            em.persist(user);

            Recipe recipe1 = Recipe.createRecipe("함초 냉이 국수", "소면 50g, 함초 3g(1/2작은술), 노루궁뎅이버섯 25g(1/2개), 다시마 17g(15cm), 파 140g(1/2개), 양파 150g(1/2개), 무 250g(1/3개), 모시조개 150g(3/4컵), 냉이 35g, 간장 30g(2큰술), 달걀 50g(1개), 청고추 20g(1개), 실고추 2g", "면", "http://www.foodsafetykorea.go.kr/uploadimg/cook/10_00131_1.png",
                    "1. 함초, 노루궁뎅이버섯, 다시마, 파, 양파, 무를 물에 넣어 10분 끓이고 향이 강한 모시조개와 냉이를 넣고 30분  끓인 후 간장으로 색을 낸다. 2. 소면은 따로 삶는다. 3. 그릇에 삶은 소면을 담은 후 육수를 붓고 지단, 슬라이스한 청고추, 실고추를 올려 완성한다.", "http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00131_1.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00131_4.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00131_5.png",
                    210L, 45, 4.5, 2);
            em.persist(recipe1);


            Recipe recipe2 = Recipe.createRecipe("구운채소", "가지 20g(3cm), 호박 50g(1/3개), 새송이버섯 15g(3개), 양파 15g(1/8개), 발사믹크레마 15g(1큰술), 빨강 파프리카 3g(3×1cm), 노랑 파프리카 3g(3×1cm), 청피망 3g(3×1cm), 올리브유 약간, 저염간장 5g(1작은술), 식초 15g(1큰술), 설탕 10g(1큰술), 레몬즙 5g(1작은술)", "채소", "http://www.foodsafetykorea.go.kr/uploadimg/cook/10_00093_1.png",
                    "1. 저염간장, 식초, 레몬즙, 설탕을 혼합하여 간장레몬 소스를 만든다. 2. 호박, 가지, 새송이버섯은 3cm 길이로 자른 후 얇은 편으로 채 썰고, 양파, 파프리카, 피망은 호박 길이로 썬다. 3. 가지, 호박, 새송이버섯, 양파, 파프리카, 피망에 올리브유를 바르고 달궈진 그릴 팬에 구운 후 접시에 담고 발사믹 크레타를 뿌리고 간장레몬 소스를 곁들인다.", "http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00093_1.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00093_2.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00093_3.png",
                    20L, 20, 5.0, 100);
            em.persist(recipe2);

            Review review1 = Review.createRecipeReview("함초 냉이 국수 후기", 3, 3.0, user, recipe1);
            em.persist(review1);

            Review review2 = Review.createRecipeReview("구운채소 후기", 4, 4.5, user, recipe2);
            em.persist(review2);

            Heart recipeHeart1 = Heart.createRecipeHeart(user, recipe1);
            em.persist(recipeHeart1);

            Heart recipeHeart2 = Heart.createRecipeHeart(user, recipe2);
            em.persist(recipeHeart2);
        }

        public void registerRecipeInit1() {
            ImageFile imageFile = ImageFile.createImageFile("군만두_사진", "jpg", null);
            em.persist(imageFile);

            Gpt gpt = Gpt.createGpt("만두", "고기피, 만두피", "1.만두 빚기 2.굽기 3.먹기");
            em.persist(gpt);

            RegisterRecipeThumbnail thumbnail = RegisterRecipeThumbnail.createThumbnail("군만두 썸네일", "jpg", null);
            em.persist(thumbnail);

            Profile profile = Profile.createProfile("profile12", "jpg", null);
            em.persist(profile);

            User user = User.createUser("testerC", "testC", passwordEncoder.encode("C1234"), 30, profile);
            em.persist(user);

            RegisterRecipe registerRecipe = RegisterRecipe.createRegisterRecipe(gpt.getFoodName(), thumbnail, "만두 먹기", "음료수랑 먹으면 맛있어요.", "기타", gpt.getIngredient(), gpt.getContext(),
                    400L, 55, 0.0, 0, user, gpt, imageFile);
            em.persist(registerRecipe);

            Review review = Review.createRegisterReview("만두 후기", 4, 4.0, user, registerRecipe);
            em.persist(review);

            Heart regiterRecipeHeart = Heart.createRegiterRecipeHeart(user, registerRecipe);
            em.persist(regiterRecipeHeart);
        }

        public void registerRecipeInit2() {
            ImageFile imageFile = ImageFile.createImageFile("새송이_버섯", "jpg", null);
            em.persist(imageFile);

            Gpt gpt = Gpt.createGpt("버섯구이", "새송이 버섯, 올리브유, 소금", "1.버섯 썰기 2.굽기 3.먹기");
            em.persist(gpt);

            RegisterRecipeThumbnail thumbnail = RegisterRecipeThumbnail.createThumbnail("버섯 썸네일", "jpg", null);
            em.persist(thumbnail);

            Profile profile = Profile.createProfile("profile1234", "png", null);
            em.persist(profile);

            User user = User.createUser("testerC", "testC", passwordEncoder.encode("C1234"), 30, profile);
            em.persist(user);


            RegisterRecipe registerRecipe = RegisterRecipe.createRegisterRecipe(gpt.getFoodName(), thumbnail, "버섯을 먹어보아요", "고기랑 같이 먹어요", "채소", gpt.getIngredient(), gpt.getContext(),
                    500L, 35, 0.0, 0, user, gpt, imageFile);
            em.persist(registerRecipe);

            Review review = Review.createRegisterReview("버섯구이 후기", 50, 5.0, user, registerRecipe);
            em.persist(review);

            Heart regiterRecipeHeart = Heart.createRegiterRecipeHeart(user, registerRecipe);
            em.persist(regiterRecipeHeart);
        }
    }
}
