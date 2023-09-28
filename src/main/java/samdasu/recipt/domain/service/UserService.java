package samdasu.recipt.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import samdasu.recipt.domain.controller.dto.User.UserSignUpDto;
import samdasu.recipt.domain.controller.dto.User.UserUpdateRequestDto;
import samdasu.recipt.domain.entity.User;
import samdasu.recipt.domain.exception.ResourceNotFoundException;
import samdasu.recipt.domain.repository.UserRepository;
import samdasu.recipt.utils.Image.AttachImage;
import samdasu.recipt.utils.Image.UploadService;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UploadService uploadService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${image.user.path}")
    private String userProfile;

    @Transactional
    public Long join(UserSignUpDto signUpDto, MultipartFile uploadFile) { //회원가입
        validateLogin(signUpDto);

        AttachImage upload = uploadService.uploadOne(userProfile, uploadFile, signUpDto.getUsername());
        log.info("upload.getSavedName() = {}", upload.getSavedName());

        User user = User.createUser(signUpDto.getUsername(), signUpDto.getLoginId(), passwordEncoder.encode(signUpDto.getPassword()), signUpDto.getAge(), upload.getSavedName());

        return userRepository.save(user).getUserId();
    }

    private void validateLogin(UserSignUpDto signUpDto) {
        userRepository.findByLoginId(signUpDto.getLoginId())
                .ifPresent(user -> {
                    throw new IllegalArgumentException("Fail: Already Exist ID!");
                });
        if (!signUpDto.getPassword().equals(signUpDto.getPasswordConfirm())) {
            throw new IllegalArgumentException("Fail: Please Check Password!");
        }
    }

    @Transactional
    public Long update(Long userId, UserUpdateRequestDto updateRequestDto) {
        User user = findUserById(userId);
        String newPassword = passwordEncoder.encode(updateRequestDto.getPassword());

        user.updateUserInfo(newPassword);
        return userId;
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No User Info"));
    }
}
