package samdasu.recipt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.controller.dto.User.UserSignUpDto;
import samdasu.recipt.controller.dto.User.UserUpdateRequestDto;
import samdasu.recipt.entity.Profile;
import samdasu.recipt.entity.User;
import samdasu.recipt.exception.ResourceNotFoundException;
import samdasu.recipt.repository.ProfileRepository;
import samdasu.recipt.repository.UserRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public Long join(UserSignUpDto signUpDto, Long profileId) { //회원가입
        validateLogin(signUpDto);
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No Profile Info"));
        User user = User.createUser(signUpDto.getUsername(), signUpDto.getLoginId(), passwordEncoder.encode(signUpDto.getPassword()), signUpDto.getAge(), profile);
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
        User user = findById(userId);

        user.updateUserInfo(updateRequestDto);
        return userId;
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No User Info"));
    }
}
