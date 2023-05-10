package samdasu.recipt.controller.dto.Gpt;

import javax.validation.constraints.NotNull;

public class GptRequestDto {
    @NotNull(message = "식재료를 입력해주세요")
    private String ingredient;

    public GptRequestDto(String ingredient) {
        this.ingredient = ingredient;
    }

    public static GptResponseDto createGptResponseDto(String foodName, String ingredient, String context) {
        return new GptResponseDto(foodName, ingredient, context);
    }
}
