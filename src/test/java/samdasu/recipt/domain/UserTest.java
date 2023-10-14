package samdasu.recipt.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.domain.entity.User;
import samdasu.recipt.domain.entity.enums.Authority;
import samdasu.recipt.domain.repository.UserRepository;
import samdasu.recipt.security.config.jwt.JwtTokenProvider;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;

@SpringBootTest
@Transactional
@Slf4j
@ActiveProfiles("test")
class UserTest {
    @PersistenceContext
    EntityManager em;

    @Test
    public void testEntity() {
        User user1 = User.createUser("user1", "id1", "pw1", 10, null,  null);
        User user2 = User.createUser("user2", "id2", "pw2", 20, null, null);
        em.persist(user1);
        em.persist(user2);

        em.flush();
        em.clear();

        List<User> users = em.createQuery("select u from User u", User.class)
                .getResultList();

        for (User user : users) {
            log.info("user.getUsername() = {}", user.getUsername());
        }

    }
}