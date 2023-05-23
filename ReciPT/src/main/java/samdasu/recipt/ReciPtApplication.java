package samdasu.recipt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import samdasu.recipt.api.db.InsertInitData;
import samdasu.recipt.api.db.InsertRecipeService;

@EnableScheduling
@SpringBootApplication
public class ReciPtApplication {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(ReciPtApplication.class, args);
        InsertRecipeService insertRecipeService = context.getBean(InsertRecipeService.class);
        InsertInitData.insertInitData(context, insertRecipeService);
    }
}