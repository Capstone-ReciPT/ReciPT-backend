package samdasu.recipt;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import samdasu.recipt.db.ExcelReader;
import samdasu.recipt.db.InsertRecipeService;
import samdasu.recipt.entity.DbRecipe;
import samdasu.recipt.repository.DbRecipe.DbRecipeRepository;

import javax.persistence.EntityManager;
import java.io.File;
import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class ReciPtApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(ReciPtApplication.class, args);
        InsertRecipeService insertRecipeService = context.getBean(InsertRecipeService.class);
        File file = new File("/Users/73630/data/test.xlsx"); // 엑셀 파일

        try {
            // 엑셀 파일에서 데이터 읽기
            List<DbRecipe> recipes = ExcelReader.readRecipesFromExcel(file);

            // 읽은 데이터 DB에 삽입
            for (DbRecipe recipe : recipes) {
                insertRecipeService.insertRecipe(recipe);
            }

            // 삽입된 레시피 개수 출력
            DbRecipeRepository dbRecipeRepository = context.getBean(DbRecipeRepository.class);
            List<DbRecipe> insertedRecipes = dbRecipeRepository.findAll();
            System.out.println("=========================");
            System.out.println("insertedRecipes.size: " + insertedRecipes.size());
            System.out.println("=========================");
        } catch (IOException e) {
            System.err.println("Error reading Excel file: " + e.getMessage());
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        SpringApplication.run(ReciPtApplication.class, args);
//    }

    @Bean
    JPAQueryFactory jpaQueryFactory(EntityManager em) {
        return new JPAQueryFactory(em);
    }
}