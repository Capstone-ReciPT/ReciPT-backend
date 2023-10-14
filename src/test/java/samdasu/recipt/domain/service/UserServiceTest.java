package samdasu.recipt.domain.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import samdasu.recipt.domain.controller.dto.User.LoginDto;
import samdasu.recipt.domain.controller.dto.User.UserResponseDto;
import samdasu.recipt.domain.controller.dto.User.UserSignUpDto;
import samdasu.recipt.domain.controller.dto.User.UserUpdateRequestDto;
import samdasu.recipt.domain.entity.User;
import samdasu.recipt.domain.entity.enums.Authority;
import samdasu.recipt.domain.exception.ResourceNotFoundException;
import samdasu.recipt.domain.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
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
    public void 유저_회원가입() throws IOException {
        //given
        UserSignUpDto signUpDto = new UserSignUpDto("tester", 10, "loginId", "password", "password");
        //when
        MultipartFile multipartFile = new MockMultipartFile("image", "test.png", "image/png", new FileInputStream("/Users/jaehyun/Pictures/뉴진스/뉴진스자바.jpeg"));
        Long savedId = userService.signUp(signUpDto, multipartFile);

        //then
        User user = userRepository.findById(savedId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No User Info"));

        assertThat(user.getUsername()).isEqualTo("tester");
        assertThat(user.getLoginId()).isEqualTo("loginId");
        assertThat(passwordEncoder.matches("password", user.getPassword())).isEqualTo(true);
    }


    @DisplayName("회원가입: 아이디 중복 에러")
    @Test
    public void AlreadyExistID() throws IOException {
        // given
        UserSignUpDto tester1 = new UserSignUpDto("tester1", 10, "duplicateId", "password", "password");
        MultipartFile multipartFile = new MockMultipartFile("image", "test.png", "image/png", new FileInputStream("/Users/jaehyun/Pictures/뉴진스/뉴진스자바.jpeg"));
        Long savedId = userService.signUp(tester1, multipartFile);

        // when: same loginId
        UserSignUpDto tester2 = new UserSignUpDto("tester2", 20, "duplicateId", "password123", "password123");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.signUp(tester2, multipartFile);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("Fail: Already Exist ID!");
    }

    @DisplayName("회원가입: 이름 중복 에러")
    @Test
    public void AlreadyExistUserName() throws IOException {
        // given
        UserSignUpDto tester1 = new UserSignUpDto("duplicateUserName", 10, "loginId1", "password1", "password1");
        MultipartFile multipartFile = new MockMultipartFile("image", "test.png", "image/png", new FileInputStream("/Users/jaehyun/Pictures/뉴진스/뉴진스자바.jpeg"));
        Long savedId = userService.signUp(tester1, multipartFile);

        // when: same userName
        UserSignUpDto tester2 = new UserSignUpDto("duplicateUserName", 20, "loginId2", "password2", "password2");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.signUp(tester2, multipartFile);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("Fail: Already Exist UserName!");
    }
    @DisplayName("회원가입: 비밀번호 != 비밀번호 확인 에러")
    @Test
    public void checkPassword() throws IOException {
        // given: not equal password & passwordConfirm
        UserSignUpDto userSignUpDto = new UserSignUpDto("tester1", 10, "loginId1", "123", "456");
        MultipartFile multipartFile = new MockMultipartFile("image", "test.png", "image/png", new FileInputStream("/Users/jaehyun/Pictures/뉴진스/뉴진스자바.jpeg"));

        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.signUp(userSignUpDto, multipartFile);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("Fail: Please Check Password!");
    }

    @Rollback(value = false)
    @DisplayName("로그인 성공")
    @Test
    public void successLogin() throws IOException {
        /**
         * 회원가입 + 로그인 두 테스트가 섞여버림... 단위테스트 X..
         */
        //given
        MultipartFile multipartFile = new MockMultipartFile("image", "test.png", "image/png", new FileInputStream("/Users/jaehyun/Pictures/뉴진스/뉴진스자바.jpeg"));
        UserSignUpDto signUpDto = new UserSignUpDto("tester", 10, "loginId", "password", "password");
        Long savedId = userService.signUp(signUpDto, multipartFile);

        User findUser = userRepository.findById(savedId).get();
        LoginDto loginDto = LoginDto.createLoginDto(findUser.getLoginId(), findUser.getPassword());
//        LoginDto loginDto = LoginDto.createLoginDto("loginId", "pw");

        //when
        userService.login(loginDto);

        //then
        ResponseEntity<?> responseEntity = ResponseEntity.ok().build();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo("로그인에 성공했습니다.");
    }

    @DisplayName("로그인 실패")
    @Test
    public void failLogin() {

    }

    @DisplayName("로그아웃")
    @Test
    public void logout() {

    }

    @DisplayName("reissue")
    @Test
    public void reissue() {

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

    private User createUser() {
        User user = User.createUser("testerA", "testA", "A1234", 30, null, Collections.singletonList(Authority.ROLE_USER.name()));
        em.persist(user);
        return user;
    }
}