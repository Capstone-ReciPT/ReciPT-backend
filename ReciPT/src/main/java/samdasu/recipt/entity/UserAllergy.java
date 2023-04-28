package samdasu.recipt.entity;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import samdasu.recipt.controller.dto.User.UserUpdateRequestDto;
import samdasu.recipt.controller.dto.allergy.UserAllergyRequestDto;
import samdasu.recipt.global.BaseTimeEntity;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAllergy extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "allergy_id")
    private Long allergyId;
    private String userAllergy;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    //==생성 메서드==// 앞으로 생성하는 지점 변경 시에는 여기만 수정하면 됨!
    public UserAllergy(String userAllergy, User user) {
        this.userAllergy = userAllergy;
        this.user = user;
    }

    public static UserAllergy createUserAllergy(String infoAllergy, User user) {
        return new UserAllergy(infoAllergy, user);
    }

    //==비지니스 로직==//
    public void updateUserAllergyInfo(){

    }
}
