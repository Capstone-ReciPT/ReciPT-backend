package samdasu.recipt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.controller.dto.Allergy.UserAllergyRequestDto;
import samdasu.recipt.controller.dto.Allergy.UserAllergyUpdateRequestDto;
import samdasu.recipt.entity.UserAllergy;
import samdasu.recipt.exception.ResourceNotFoundException;
import samdasu.recipt.repository.UserAllergyRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserAllergyService {
    private final UserAllergyRepository userAllergyRepository;

    @Transactional
    public Long saveUserAllergyInfo(UserAllergyRequestDto userAllergyRequestDto) { //회원가입
        validation(userAllergyRequestDto);

        UserAllergy userAllergy = UserAllergy.createUserAllergy(userAllergyRequestDto.getUserAllergy(), userAllergyRequestDto.getUser());
        return userAllergyRepository.save(userAllergy).getAllergyId();
    }


    private void validation(UserAllergyRequestDto userAllergyRequestDto) {
        userAllergyRepository.findByUserAllergyAndUser(userAllergyRequestDto.getUserAllergy(), userAllergyRequestDto.getUser())
                .ifPresent(userAllergy -> {
                    throw new IllegalArgumentException("Fail: Already Exist UserAllergyInfo!");
                });
    }

    @Transactional
    public Long update(Long allergyId, UserAllergyUpdateRequestDto userAllergyUpdateRequestDto) {
        UserAllergy userAllergy = userAllergyRepository.findById(allergyId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No AllergyInfo Info"));

        userAllergy.updateUserAllergyInfo(userAllergyUpdateRequestDto);
        return allergyId;
    }
}
