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
 * searchDynamicSearching
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
