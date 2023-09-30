package samdasu.recipt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import samdasu.recipt.domain.aop.*;

@EnableScheduling
@SpringBootApplication
@Import({UserAspect.class, ReviewAspect.class, RegisterRecipeAspect.class, RecipeAspect.class, HeartAspect.class, GptAspect.class})
public class ReciPtApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(ReciPtApplication.class, args);
    }
}