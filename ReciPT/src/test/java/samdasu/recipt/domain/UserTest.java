package samdasu.recipt.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.entity.User;
import samdasu.recipt.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@SpringBootTest
@Transactional
@Rollback(value = false)
class UserTest {
    @PersistenceContext
    EntityManager em;


    private final UserRepository userRepository;

    @Autowired
    UserTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Test
    public void testEntity() {
        User user1 = new User("user1", "id1", "pw1", "shrimp");
        User user2 = new User("user2", "id2", "pw2", "milk");
        em.persist(user1);
        em.persist(user2);

        em.flush();
        em.clear();

        List<User> users = em.createQuery("select u from User u", User.class)
                .getResultList();

        for (User user : users) {
            System.out.println("user = " + user);
        }

        User user = userRepository.findById(Long.valueOf(1))
                .orElseThrow(() -> new IllegalArgumentException("Fail: Empty User Id 1"));
        System.out.println("user.getUsername() = " + user.getUsername());


    }
}