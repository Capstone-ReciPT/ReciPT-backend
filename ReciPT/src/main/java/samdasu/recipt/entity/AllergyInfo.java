package samdasu.recipt.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AllergyInfo {
    @Id
    @GeneratedValue
    @Column(name = "allergy_info_id")
    private Long id;

    private String category;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "allergy_id")
    private Allergy allergy;

    //== 연관관계 편의 메서드==//

    public void changeUser(User user) {
        this.user = user;
        user.getAllergyInfos().add(this);
    }

    public void changeAllergy(Allergy allergy) {
        this.allergy = allergy;
        user.getAllergyInfos().add(this);
    }

    //==생성 메서드==// 앞으로 생성하는 지점 변경 시에는 여기만 수정하면 됨!
    public AllergyInfo(String category, User user, Allergy allergy) {
        this.category = category;
        if (user != null) {
            changeUser(user);
        }
        if (allergy != null) {
            changeAllergy(allergy);
        }
    }
}
