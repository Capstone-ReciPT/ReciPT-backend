package samdasu.recipt.db;

import org.springframework.stereotype.Component;
import samdasu.recipt.entity.DbRecipe;

import java.io.File;
import java.io.IOException;
import java.util.List;


@Component
public class RecipeImporter {
    public void importRecipesFromExcel(File file) {
        try {
            List<DbRecipe> recipes = ExcelReader.readRecipesFromExcel(file);
        } catch (IOException e) {
            System.out.println("Error reading recipes from Excel file: " + e.getMessage());
        }
    }
}