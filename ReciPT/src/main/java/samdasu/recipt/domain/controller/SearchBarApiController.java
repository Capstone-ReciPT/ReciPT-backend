package samdasu.recipt.domain.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import samdasu.recipt.domain.controller.dto.User.UserResponseDto;
import samdasu.recipt.domain.entity.Recipe;
import samdasu.recipt.domain.entity.RegisterRecipe;
import samdasu.recipt.domain.entity.User;
import samdasu.recipt.domain.service.RecipeService;
import samdasu.recipt.domain.service.RegisterRecipeService;
import samdasu.recipt.domain.service.UserService;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SearchBarApiController {
    private final UserService userService;
    private final RecipeService recipeService;
    private final RegisterRecipeService registerRecipeService;

    @Autowired
    private DataSource dataSource;

    @GetMapping("/search")
    public Result recommendByAge(@AuthenticationPrincipal UserResponseDto userResponseDto) throws SQLException {
        List<RegisterRecipe> registerRecipes = registerRecipeService.findRegisterRecipes();
        List<String> recommend;

        if (Optional.ofNullable(userResponseDto).isPresent()) { //로그인 한 경우
            User findUser = userService.findById(userResponseDto.getUserId());
            if (registerRecipes.size() < 10) {
                recommend = randomRecommend();
            } else {
                recommend = registerRecipeService.RecommendByAge(findUser.getAge());
            }
        } else { //로그인 하지 않은 경우
            recommend = randomRecommend();
        }
        return new Result(recommend.size(), recommend);
    }

    private List<String> randomRecommend() throws SQLException {
        List<String> recommend;
        String databaseUrl = dataSource.getConnection().getMetaData().getURL();
        log.info("databaseUrl = {}", databaseUrl);
        if (databaseUrl.contains("h2")) {
            recommend = recipeService.RecommendByRandH2();
        } else {
            recommend = recipeService.RecommendByRandMySql();
        }
        return recommend;
    }

    @PostMapping("/search")
    public Result searchingFood(@AuthenticationPrincipal UserResponseDto userResponseDto,
                                @RequestParam(value = "foodName", required = false) String foodName,
                                @RequestParam(value = "like", required = false) Integer like,
                                @RequestParam(value = "view", required = false) Long view) {
        List<String> collect = new ArrayList<>();

        List<Recipe> recipes = recipeService.searchDynamicSearching(foodName, like, view);

        for (Recipe recipe : recipes) {
            collect.add(recipe.getFoodName());
        }
        List<RegisterRecipe> registerRecipes = registerRecipeService.searchDynamicSearching(foodName, like, view);

        for (RegisterRecipe registerRecipe : registerRecipes) {
            collect.add(registerRecipe.getFoodName());
        }
        return new Result(recipes.size() + registerRecipes.size(), collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count; //특정 List의 개수 (ex. 사용자가 쓴 리뷰 개수)
        private T foodName;
    }
}
