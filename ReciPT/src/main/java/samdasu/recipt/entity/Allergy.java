package samdasu.recipt.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Allergy extends BaseTimeEntity {
    private String category;
    private String causedIngredient;


    public Allergy(String category, String causedIngredient) {
        this.category = category;
        this.causedIngredient = causedIngredient;
    }
}
