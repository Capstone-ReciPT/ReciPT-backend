package samdasu.recipt.api.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import samdasu.recipt.domain.entity.Recipe;
import samdasu.recipt.domain.repository.Recipe.RecipeRepository;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class InsertInitData {

    private static String excelFilePath;
    private final InsertRecipeService insertRecipeService;
    private final RecipeRepository recipeRepository;

    public InsertInitData(@Value("${excel.file.path}") String excelFile, InsertRecipeService insertRecipeService, RecipeRepository recipeRepository) {
        this.excelFilePath = excelFile;
        this.insertRecipeService = insertRecipeService;
        this.recipeRepository = recipeRepository;
    }

    @PostConstruct
    public void insertInitData() {
        log.info("excelFilePath = {}", excelFilePath);
        try {
            // 엑셀 파일에서 데이터 읽기
            List<Recipe> recipes = ExcelReader.readRecipesFromExcel(new File(excelFilePath));

            // 읽은 데이터 DB에 삽입
            for (Recipe recipe : recipes) {
                insertRecipeService.insertRecipe(recipe);
            }

            // 삽입된 레시피 개수 출력
            List<Recipe> insertedRecipes = recipeRepository.findAll();
            log.info("=========================");
            log.info("insertedRecipes.size: {}", insertedRecipes.size());
            log.info("=========================");
        } catch (IOException e) {
            log.debug("Error reading Excel file: ", e.getMessage());
            e.printStackTrace();
        }
    }
}

