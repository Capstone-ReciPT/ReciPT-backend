package samdasu.recipt.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.controller.dto.UserSignUpDto;
import samdasu.recipt.entity.User;
import samdasu.recipt.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.Connection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {

    private final UserRepository userRepository;
    private final UserService userService;

    private final BCryptPasswordEncoder passwordEncoder;
    private final DataSource dataSource;

    @Autowired
    public UserServiceTest(UserRepository userRepository, UserService userService, BCryptPasswordEncoder passwordEncoder, DataSource dataSource) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.dataSource = dataSource;
    }

    @BeforeAll
    public void init() {
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("/db/h2/data.sql"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void 유저_회원가입() {
        //given
        UserSignUpDto signUpDto = new UserSignUpDto("tester", "testId", "test1234", "test1234", "shrimp");
//        UserSignUpDto kim = new UserSignUpDto("kim", "id2", "5678", "5678", "milk");

        //when
        Long savedId = userService.join(signUpDto);
//        Long savedKim = userService.join(kim);

        //then
        User user = userRepository.findById(savedId).get();

        assertThat(user.getUserName()).isEqualTo("tester");
        assertThat(user.getLoginId()).isEqualTo("testId");
        assertThat(passwordEncoder.matches("test1234", user.getPassword())).isEqualTo(true);
    }

    @Test
    public void 유저_회원가입_중복회원() {
        // given
        UserSignUpDto tester1 = new UserSignUpDto("tester1", "testId", "test1234", "test1234", "shrimp");


        Long savedId = userService.join(tester1);

        // when
        // same loginId
        UserSignUpDto tester2 = new UserSignUpDto("tester2", "testId", "test5678", "test5678", "milk");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.join(tester2);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("Fail: Already Exist ID!");
    }
}