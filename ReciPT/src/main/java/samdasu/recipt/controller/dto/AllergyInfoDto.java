package samdasu.recipt.controller.dto;

import lombok.Getter;
import samdasu.recipt.entity.AllergyInfo;

@Getter
public class AllergyInfoDto {
    private String userAllergy;
    private String category;

    public AllergyInfoDto(AllergyInfo allergyInfo) {
        this.userAllergy = allergyInfo.getUser().getUserAllergy();
        this.category = allergyInfo.getCategory();
    }
}
