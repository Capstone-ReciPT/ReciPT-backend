package samdasu.recipt.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.controller.dto.Allergy.UserAllergyRequestDto;
import samdasu.recipt.controller.dto.Allergy.UserAllergyResponseDto;
import samdasu.recipt.controller.dto.Allergy.UserAllergyUpdateRequestDto;
import samdasu.recipt.entity.User;
import samdasu.recipt.entity.UserAllergy;
import samdasu.recipt.exception.ResourceNotFoundException;
import samdasu.recipt.repository.UserAllergyRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserAllergyServiceTest {
    @PersistenceContext
    EntityManager em;
    @Autowired
    UserAllergyRepository userAllergyRepository;
    @Autowired
    UserAllergyService userAllergyService;

    @Test
    @Rollback(value = false)
    public void 알러지_저장() throws Exception {
        //given
        User user = createUser();
        UserAllergyRequestDto userAllergyRequestDto = getUserAllergyRequestDto(user);

        //when
        Long saveUserAllergyInfo = userAllergyService.saveUserAllergyInfo(userAllergyRequestDto);

        //then
        UserAllergy userAllergy = userAllergyRepository.findById(saveUserAllergyInfo)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No UserAllergy Info"));

        assertThat(userAllergy.getUser().getUsername()).isEqualTo("tester1");
        assertThat(userAllergy.getUserAllergy()).isEqualTo("milk");
    }

    @Test
    public void 알러지_중복() {
        // given
        User user = createUser();
        UserAllergyRequestDto userAllergyRequestDto = getUserAllergyRequestDto(user);

        userAllergyService.saveUserAllergyInfo(userAllergyRequestDto);

        // when
        // same 알러지 정보
        UserAllergyRequestDto sameUserAllergyInfo = getUserAllergyRequestDto(user);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userAllergyService.saveUserAllergyInfo(sameUserAllergyInfo);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("Fail: Already Exist UserAllergyInfo!");
    }


    @Test
    public void 알러지_정보_조회() {
        //given
        UserAllergy allergy = createAllergy();
        UserAllergyResponseDto userAllergyResponseDto = UserAllergyResponseDto.createUserAllergyResponseDto(allergy);

        //when
        UserAllergyResponseDto findUserAllergy = findUserAllergyResponseDtoById(userAllergyResponseDto.getAllergyId());

        //then
        assertThat(findUserAllergy.getAllergyId()).isEqualTo(allergy.getAllergyId());
        assertThat(findUserAllergy.getUserAllergy()).isEqualTo(allergy.getUserAllergy());
    }

    @Test
    public void 유저_업데이트() {
        //given
        UserAllergy allergy = createAllergy();
        UserAllergyResponseDto userAllergyResponseDto = UserAllergyResponseDto.createUserAllergyResponseDto(allergy);

        //when
        //change password & Allergy Info
        UserAllergyUpdateRequestDto changeAllergy = UserAllergyUpdateRequestDto.createUserAllergyRequestDto("changeAllergy");
        Long updateUserInfo = userAllergyService.update(userAllergyResponseDto.getAllergyId(), changeAllergy);

        //then
        UserAllergyResponseDto updateAllergy = findUserAllergyResponseDtoById(updateUserInfo);

        assertThat(updateAllergy.getUserAllergy()).isEqualTo(changeAllergy.getUserAllergy());
    }

    private User createUser() {
        User user = User.createUser("tester1", "testId", "test1234");
        em.persist(user);

        return user;
    }

    private UserAllergyRequestDto getUserAllergyRequestDto(User user) {
        return UserAllergyRequestDto.createUserAllergyRequestDto("milk", user);
    }

    private UserAllergy createAllergy() {
        User user = createUser();
        UserAllergyRequestDto userAllergyRequestDto = getUserAllergyRequestDto(user);
        UserAllergy userAllergy = UserAllergy.createUserAllergy(userAllergyRequestDto);
        em.persist(userAllergy);
        
        return userAllergy;
    }

    public UserAllergyResponseDto findUserAllergyResponseDtoById(Long userAllergyId) {
        UserAllergy userAllergy = userAllergyRepository.findById(userAllergyId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No UserAllergy Info"));
        return new UserAllergyResponseDto(userAllergy);
    }
}