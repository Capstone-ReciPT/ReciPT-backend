package samdasu.recipt.db;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import samdasu.recipt.entity.Recipe;
import samdasu.recipt.repository.Recipe.RecipeRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Configuration
public class InsertInitData {

    private static String excelFilePath;

    public InsertInitData(@Value("${excel.file.path}") String excelFilePath) {
        InsertInitData.excelFilePath = excelFilePath;
    }

    public static void insertInitData(ApplicationContext context, InsertRecipeService insertRecipeService) {
        try {
            // 엑셀 파일에서 데이터 읽기
            List<Recipe> recipes = ExcelReader.readRecipesFromExcel(new File(excelFilePath));

            // 읽은 데이터 DB에 삽입
            for (Recipe recipe : recipes) {
                insertRecipeService.insertRecipe(recipe);
            }

            // 삽입된 레시피 개수 출력
            RecipeRepository recipeRepository = context.getBean(RecipeRepository.class);
            List<Recipe> insertedRecipes = recipeRepository.findAll();
            System.out.println("=========================");
            System.out.println("insertedRecipes.size: " + insertedRecipes.size());
            System.out.println("=========================");
        } catch (IOException e) {
            System.out.println("Error reading Excel file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

