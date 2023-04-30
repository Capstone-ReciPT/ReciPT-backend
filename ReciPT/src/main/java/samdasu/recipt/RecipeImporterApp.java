//package samdasu.recipt;
//
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.*;
//import samdasu.recipt.entity.DbRecipe;
//import samdasu.recipt.repository.DbRecipe.DbRecipeRepository;
//import samdasu.recipt.db.ExcelReader;
//import samdasu.recipt.db.InsertRecipeService;
//import samdasu.recipt.db.RecipeImporter;
//
//import javax.persistence.EntityManager;
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//
//@SpringBootApplication
//@Configuration
//public class RecipeImporterApp {
//    @Bean
//    JPAQueryFactory jpaQueryFactory(EntityManager em) {
//        return new JPAQueryFactory(em);
//    }
//
//    private static final String EXCEL_FILE_PATH = "C:/Users/73630/data/test.xlsx";
//
//    public static void main(String[] args) {
//        ApplicationContext context = SpringApplication.run(RecipeImporterApp.class, args);
//        InsertRecipeService insertRecipeService = context.getBean(InsertRecipeService.class);
//        RecipeImporter importer = new RecipeImporter();
//        File file = new File(EXCEL_FILE_PATH); //엑셀 파일 경로
//        importer.importRecipesFromExcel(file);
//
//        try {
//            // 엑셀 파일에서 데이터 읽기
//            List<DbRecipe> recipes = ExcelReader.readRecipesFromExcel(new File(EXCEL_FILE_PATH));
//
//            // 읽은 데이터 DB에 삽입
//            for (DbRecipe recipe : recipes) {
//                insertRecipeService.insertRecipe(recipe);
//            }
//
//            // 삽입된 레시피 개수 출력
//            DbRecipeRepository dbRecipeRepository = context.getBean(DbRecipeRepository.class);
//            List<DbRecipe> insertedRecipes = dbRecipeRepository.findAll();
//            System.out.println("=========================");
//            System.out.println("insertedRecipes.size: " + insertedRecipes.size());
//            System.out.println("=========================");
//        } catch (IOException e) {
//            System.out.println("Error reading Excel file: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//}
