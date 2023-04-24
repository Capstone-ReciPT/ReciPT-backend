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
import samdasu.recipt.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

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

    @Test
    public void 유저_회원정보_조회() {
        //given
        User user = User.createUser("tester1", "testId", "test1234", "shrimp");
        User savedUser = userRepository.save(user);
        UserResponseDto userResponseDto = new UserResponseDto(savedUser);

        //when
        UserResponseDto findUser = userService.findById(userResponseDto.getUserId());

        //then
        assertThat(findUser.getUserId()).isEqualTo(user.getUserId());
        assertThat(findUser.getUserName()).isEqualTo(user.getUserName());
        assertThat(findUser.getLoginId()).isEqualTo(user.getLoginId());
    }

    @Test
    public void 유저_업데이트() {
        //given
        User user = User.createUser("tester1", "testId", "test1234", "shrimp");
        User savedUser = userRepository.save(user);
        UserResponseDto userResponseDto = new UserResponseDto(savedUser);

        //when
        //change password & Allergy Info
        UserUpdateRequestDto userUpdateRequestDto = UserUpdateRequestDto.createUpdateUserInfo("changePassword", "changeAllergy");
        Long updateUserInfo = userService.update(userResponseDto.getUserId(), userUpdateRequestDto);


        //then
        assertThat(updateUserInfo).isEqualTo(userResponseDto.getUserId());
        UserResponseDto updateUser = userService.findById(updateUserInfo);
        assertThat(updateUser.getPassword()).isEqualTo(userUpdateRequestDto.getPassword());
        assertThat(updateUser.getUserAllergy()).isEqualTo(userUpdateRequestDto.getUserAllergy());
    }
}