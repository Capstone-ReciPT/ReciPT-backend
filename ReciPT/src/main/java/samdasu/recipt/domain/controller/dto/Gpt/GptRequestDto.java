package samdasu.recipt.domain.controller.dto.Gpt;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class GptRequestDto {
    @NotNull(message = "식재료를 입력해주세요")
    private String ingredient;

    public GptRequestDto(String ingredient) {
        this.ingredient = ingredient;
    }

    public static GptRequestDto createGptRequestDto(String ingredient) {
        return new GptRequestDto(ingredient);
    }
}

