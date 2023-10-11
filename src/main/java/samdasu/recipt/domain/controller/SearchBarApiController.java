package samdasu.recipt.domain.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import samdasu.recipt.domain.controller.dto.Heart.RegisterHeartDto;
import samdasu.recipt.domain.controller.dto.Recipe.RecipeShortResponseDto;
import samdasu.recipt.domain.controller.dto.Register.RegisterRecipeShortResponseDto;
import samdasu.recipt.domain.controller.dto.User.UserResponseDto;
import samdasu.recipt.domain.entity.Recipe;
import samdasu.recipt.domain.entity.RegisterRecipe;
import samdasu.recipt.domain.entity.User;
import samdasu.recipt.domain.service.RecipeService;
import samdasu.recipt.domain.service.RegisterRecipeService;
import samdasu.recipt.domain.service.UserService;
import samdasu.recipt.utils.Image.UploadService;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static samdasu.recipt.domain.controller.constant.ControllerStandard.STANDARD;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
public class SearchBarApiController {
    private final UserService userService;
    private final RecipeService recipeService;
    private final RegisterRecipeService registerRecipeService;

    private final UploadService uploadService;

    @Autowired
    private DataSource dataSource;

    @GetMapping
    public Result recommendByAge(Authentication authentication) throws SQLException {
        List<RegisterRecipe> registerRecipes = registerRecipeService.findRegisterRecipes();
        List<String> recommend;

        User findUser = userService.findUserByUsername(authentication.getName());

        if (registerRecipes.size() < STANDARD) {
            recommend = randomRecommend();
        } else {
            recommend = registerRecipeService.RecommendByAge(findUser.getAge());
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


    @PostMapping("/recipes")
    public Result2 searchingRecipes(
            @RequestParam(value = "foodName", required = false) String foodName,
            @RequestParam(value = "like", required = false) Integer like,
            @RequestParam(value = "view", required = false) Long view) {
        List<Recipe> recipes = recipeService.searchDynamicSearching(foodName, like, view);
        List<RecipeShortResponseDto> recipe = getRecipesByCategory(recipes);

        List<RegisterRecipe> registerRecipes = registerRecipeService.searchDynamicSearching(foodName, like, view);
        List<RegisterRecipeShortResponseDto> registerShortResponseDtos = getRegisterRecipesByCategory(registerRecipes);

        for (int i = 0; i < registerShortResponseDtos.size(); i++) {
            byte[] registerThumbnail = uploadService.getRegisterProfile(registerShortResponseDtos.get(i).getUsername(), registerShortResponseDtos.get(i).getThumbnailImage());
            registerShortResponseDtos.get(i).setThumbnailImageByte(registerThumbnail);
        }

        return new Result2(recipe.size(), registerShortResponseDtos.size(), recipe, registerShortResponseDtos);
    }

    private List<RecipeShortResponseDto> getRecipesByCategory(List<Recipe> recipeCategory) {
        List<RecipeShortResponseDto> collect1 = recipeCategory.stream()
                .map(RecipeShortResponseDto::new)
                .collect(Collectors.toList());
        return collect1;
    }

    private List<RegisterRecipeShortResponseDto> getRegisterRecipesByCategory(List<RegisterRecipe> registerRecipes) {
        List<RegisterRecipeShortResponseDto> registerRecipeShortResponseDtos = registerRecipes.stream()
                .map(RegisterRecipeShortResponseDto::new)
                .collect(Collectors.toList());
        return registerRecipeShortResponseDtos;
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count; //특정 List의 개수 (ex. 사용자가 쓴 리뷰 개수)
        private T foodName;
    }

    @Data
    @AllArgsConstructor
    static class Result2<T> {
        private int recipeCount; // 레시피 수
        private int registerRecipeCount; // 레시피 수
        private T recipeList;
        private T registerRecipeList;
    }
}
