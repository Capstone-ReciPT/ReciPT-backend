package samdasu.recipt.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.controller.dto.User.UserResponseDto;
import samdasu.recipt.controller.dto.User.UserSignUpDto;
import samdasu.recipt.controller.dto.User.UserUpdateRequestDto;
import samdasu.recipt.entity.User;
import samdasu.recipt.exception.ResourceNotFoundException;
import samdasu.recipt.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {
    @PersistenceContext
    EntityManager em;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Test
    public void 유저_회원가입() {
        //given
        UserSignUpDto signUpDto = createUserSignUpDto("tester", 10, "test1234");

        //when
        Long savedId = userService.join(signUpDto);

        //then
        User user = userRepository.findById(savedId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No User Info"));

        assertThat(user.getUsername()).isEqualTo("tester");
        assertThat(user.getLoginId()).isEqualTo("testId");
        assertThat(passwordEncoder.matches("test1234", user.getPassword())).isEqualTo(true);
    }

    @Test
    public void 유저_회원가입_중복회원() {
        // given
        UserSignUpDto tester1 = createUserSignUpDto("tester", 10, "test1234");

        userService.join(tester1);

        // when
        // same loginId
        UserSignUpDto tester2 = createUserSignUpDto("tester2", 20, "test5678");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.join(tester2);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("Fail: Already Exist ID!");
    }


    @Test
    public void 유저_회원정보_조회() {
        //given
        User testA = createUser();

        //when
        UserResponseDto findUser = findUserResponseDtoById(testA.getUserId());

        //then
        assertThat(findUser.getUserId()).isEqualTo(testA.getUserId());
        assertThat(findUser.getUsername()).isEqualTo(testA.getUsername());
        assertThat(findUser.getLoginId()).isEqualTo(testA.getLoginId());
    }

    @Test
    public void 유저_업데이트() {
        //given
        User testA = createUser();

        //when
        //change password
        UserUpdateRequestDto userUpdateRequestDto = UserUpdateRequestDto.createUpdateUserInfo("changePassword", null);
        Long updateUserInfo = userService.update(testA.getUserId(), userUpdateRequestDto);

        //then
        UserResponseDto updateUser = findUserResponseDtoById(updateUserInfo);

        assertThat(updateUser.getPassword()).isEqualTo(userUpdateRequestDto.getPassword());
    }

    public UserResponseDto findUserResponseDtoById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No User Info"));
        return new UserResponseDto(user);
    }

    private User createUser() {
        User user = User.createUser("testerA", null, "testA", "A1234", 30);
        em.persist(user);
        return user;
    }

    private static UserSignUpDto createUserSignUpDto(String tester, int age, String test1234) {
        return new UserSignUpDto(tester, null, age, "testId", test1234, test1234);
    }
}