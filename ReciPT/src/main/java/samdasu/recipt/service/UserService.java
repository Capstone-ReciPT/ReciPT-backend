package samdasu.recipt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.controller.dto.UserResponseDto;
import samdasu.recipt.controller.dto.UserSignUpDto;
import samdasu.recipt.entity.User;
import samdasu.recipt.exception.ResourceNotFoundException;
import samdasu.recipt.repository.UserRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public Long join(UserSignUpDto signUpDto) {
        userRepository.findByLoginId(signUpDto.getLoginId())
                .ifPresent(user -> {
                    throw new IllegalArgumentException("Fail: Already Exist ID!");
                });
        if (!signUpDto.getPassword().equals(signUpDto.getPasswordConfirm())) {
            throw new IllegalArgumentException("Fail: Please Check Password!");
        }

        User user = new User(signUpDto.getUserName(), signUpDto.getLoginId(), signUpDto.getPassword(), userRepository.findByUserAllergy(signUpDto.getUserAllergy()));
        return userRepository.save(user).getUserId();
    }

    public UserResponseDto findById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail:No UserResponseDto Info"));
        return new UserResponseDto(user);
    }

}
