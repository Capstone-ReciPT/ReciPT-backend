package samdasu.recipt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.controller.dto.User.UserResponseDto;
import samdasu.recipt.controller.dto.User.UserSignUpDto;
import samdasu.recipt.controller.dto.User.UserUpdateRequestDto;
import samdasu.recipt.entity.User;
import samdasu.recipt.exception.ResourceNotFoundException;
import samdasu.recipt.repository.UserRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public Long join(UserSignUpDto signUpDto) { //회원가입
        validateLogin(signUpDto);
        User user = User.createUser(signUpDto.getUserName(), signUpDto.getLoginId(), passwordEncoder.encode(signUpDto.getPassword()), signUpDto.getUserAllergy());
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

    public UserResponseDto findById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No User Info"));
        return new UserResponseDto(user);
    }

    @Transactional
    public Long update(Long userId, UserUpdateRequestDto updateRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No User Info"));

        user.updateUserInfo(updateRequestDto);
        return userId;
    }

}
