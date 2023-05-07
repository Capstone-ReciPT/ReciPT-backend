package samdasu.recipt.db;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import samdasu.recipt.entity.Recipe;
import samdasu.recipt.repository.Recipe.RecipeRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Configuration
public class InsertInitData {

    private static final String EXCEL_FILE_PATH = "/Users/jaehyun/Documents/IdeaProjects/ReciPT/ReciPT-backend/test.xlsx";

    public static void insertInitData(ApplicationContext context, InsertRecipeService insertRecipeService) {
        try {
            // 엑셀 파일에서 데이터 읽기
            List<Recipe> recipes = ExcelReader.readRecipesFromExcel(new File(EXCEL_FILE_PATH));

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

