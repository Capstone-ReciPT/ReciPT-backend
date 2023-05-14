package samdasu.recipt.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.dialect.H2Dialect;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import samdasu.recipt.controller.dto.User.UserResponseDto;
import samdasu.recipt.entity.RegisterRecipe;
import samdasu.recipt.entity.User;
import samdasu.recipt.service.RecipeService;
import samdasu.recipt.service.RegisterRecipeService;
import samdasu.recipt.service.UserService;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.PersistenceUnitUtil;
import java.util.List;

/**
 * SELECT age_group, food_name, total_likes
 * FROM (
 * SELECT
 * CASE
 * WHEN users.age BETWEEN 10 AND 19 THEN '10대'
 * WHEN users.age BETWEEN 20 AND 29 THEN '20대'
 * -- 다른 연령대에 대한 CASE 문 추가
 * ELSE '기타'
 * END AS age_group,
 * register_recipe.food_name,
 * SUM(register_recipe.like_count) AS total_likes
 * FROM users
 * JOIN register_recipe ON users.user_Id = register_recipe.user_Id
 * GROUP BY age_group, register_recipe.food_name
 * ) AS subquery
 * WHERE age_group != '기타'
 * ORDER BY age_group, total_likes DESC
 * LIMIT 10;
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SearchBarApiController {
    private final UserService userService;
    private final RecipeService recipeService;
    private final RegisterRecipeService registerRecipeService;

    @PersistenceUnit
    private final EntityManagerFactory emf;

    @GetMapping("/search")
    public Result recommendByAge(@AuthenticationPrincipal UserResponseDto userResponseDto) {
        List<RegisterRecipe> registerRecipes = registerRecipeService.findRegisterRecipes();
        User findUser = userService.findById(userResponseDto.getUserId());
        List<String> recommend;


        if (registerRecipes.size() > 10) {
            PersistenceUnitUtil persistenceUnitUtil = emf.getPersistenceUnitUtil();
            boolean isH2 = persistenceUnitUtil.getIdentifier(findUser) instanceof H2Dialect;

            if (isH2) {
                recommend = recipeService.RecommendByRandH2();
            } else {
                recommend = recipeService.RecommendByRandMySql();
            }
        } else {
            recommend = registerRecipeService.RecommendByAge(findUser.getAge());
        }

        return new Result(recommend.size(), recommend);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count; //특정 List의 개수 (ex. 사용자가 쓴 리뷰 개수)
        private T data;
    }
}
