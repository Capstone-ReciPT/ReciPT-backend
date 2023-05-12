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
import samdasu.recipt.entity.Profile;
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
        Profile profile = createProfile();
        UserSignUpDto signUpDto = createUserSignUpDto("tester", profile, 10, "test1234");
        //when
        Long savedId = userService.join(signUpDto, profile.getProfileId());

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
        Profile profile = createProfile();
        UserSignUpDto tester1 = createUserSignUpDto("tester", profile, 10, "test1234");
        userService.join(tester1, profile.getProfileId());

        // when
        // same loginId
        UserSignUpDto tester2 = createUserSignUpDto("tester2", profile, 20, "test5678");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.join(tester2, profile.getProfileId());
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("Fail: Already Exist ID!");
    }


    @Test
    public void 유저_회원정보_조회() {
        //given
        Profile profile = createProfile();
        User testA = createUser(profile);

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
        Profile profile = createProfile();
        User testA = createUser(profile);

        //when
        //change password
        UserUpdateRequestDto userUpdateRequestDto = UserUpdateRequestDto.createUpdateUserInfo("changePassword");
        Long updateUserInfo = userService.update(testA.getUserId(), userUpdateRequestDto);

        //then
        UserResponseDto updateUser = findUserResponseDtoById(updateUserInfo);

        assertThat(updateUser.getPassword()).isEqualTo(testA.getPassword());
    }

    public UserResponseDto findUserResponseDtoById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No User Info"));
        return new UserResponseDto(user);
    }

    private User createUser(Profile profile) {
        User user = User.createUser("testerA", "testA", "A1234", 30, profile);
        em.persist(user);
        return user;
    }

    private Profile createProfile() {
        Profile profile = Profile.createProfile("프로필 사진", "jpg", null);
        em.persist(profile);
        return profile;
    }

    private static UserSignUpDto createUserSignUpDto(String tester, Profile profile, int age, String password) {
        return new UserSignUpDto(tester, profile, age, "testId", password, password);
    }
}