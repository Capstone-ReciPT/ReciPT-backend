package samdasu.recipt.controller.dto.Gpt;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class GptResponseDto {
    @NotNull
    private String foodName;
    @NotNull
    private String ingredient;
    @NotNull
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
