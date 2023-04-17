package samdasu.recipt.service.dto;

import lombok.Getter;
import samdasu.recipt.entity.AllergyInfo;

@Getter
public class AllergyInfoDto {
    private String userAllergy;
    private String category;

    public AllergyInfoDto(AllergyInfo allergyInfo) {
        userAllergy = allergyInfo.getUser().getUserAllergy();
        category = allergyInfo.getCategory();
    }
}
