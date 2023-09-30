package samdasu.recipt.domain.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import samdasu.recipt.domain.controller.dto.User.*;
import samdasu.recipt.domain.entity.User;
import samdasu.recipt.domain.service.UserService;
import samdasu.recipt.domain.service.lib.ErrorTemplate;

import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class LoginController {

    private final UserService userService;
    private final LoginResponse response;
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid UserSignUpDto signUp, @RequestParam(value = "profile") MultipartFile file, Errors errors) throws IOException {

        Long signUpId = userService.signUp(signUp, file);
        User findUser = userService.findUserById(signUpId);

        log.info("findUser.getUsername = {}", findUser.getUsername());
        log.info("findUser.getProfile() ={}", findUser.getProfile());

        return response.success("회원가입에 성공했습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto login, Errors errors) {

        if (errors.hasErrors()) {
            return response.invalidFields(ErrorTemplate.refineErrors(errors));
        }

        return userService.login(login);
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@Validated UserReissueDto reissue, Errors errors) {

        if (errors.hasErrors()) {
            return response.invalidFields(ErrorTemplate.refineErrors(errors));
        }

        return userService.reissue(reissue);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@Validated UserLogoutDto logout, Errors errors) {

        if (errors.hasErrors()) {
            return response.invalidFields(ErrorTemplate.refineErrors(errors));
        }

        log.info("Logout successful!");
        return userService.logout(logout);
    }

    @GetMapping("/authority")
    public ResponseEntity<?> authority() {
        log.info("ADD ROLE_ADMIN");
        return userService.authority();
    }

    @GetMapping("/userTest")
    public ResponseEntity<?> userTest() {
        log.info("ROLE_USER TEST");
        return response.success();
    }

    @GetMapping("/adminTest")
    public ResponseEntity<?> adminTest() {
        log.info("ROLE_ADMIN TEST");
        return response.success();
    }
}
