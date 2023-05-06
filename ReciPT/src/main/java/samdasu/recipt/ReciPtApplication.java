package samdasu.recipt;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;

@SpringBootApplication
public class ReciPtApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(ReciPtApplication.class, args);
//        InsertRecipeService insertRecipeService = context.getBean(InsertRecipeService.class);
//        InsertInitData.insertInitData(context, insertRecipeService);
    }

    @Bean
    JPAQueryFactory jpaQueryFactory(EntityManager em) {
        return new JPAQueryFactory(em);
    }
}