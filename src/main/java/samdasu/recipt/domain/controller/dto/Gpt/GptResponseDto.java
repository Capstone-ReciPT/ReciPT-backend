package samdasu.recipt.domain.controller.dto.Gpt;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GptResponseDto {

    private String foodName;

    private String ingredient;

    private String context;

    public GptResponseDto(String foodName, String ingredient, String context) {
        this.foodName = foodName;
        this.ingredient = ingredient;
        this.context = context;
    }

    public static GptResponseDto createGptResponseDto(String foodName, String ingredient, String context) {
        return new GptResponseDto(foodName, ingredient, context);
    }

}
