package samdasu.recipt.api.gpt.controller.dto;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.domain.entity.Gpt;

@Getter
@Setter
public class ChatGptRecipeSaveResponseDto {

    private String foodName;

    private String ingredient;

    private String context;


    public ChatGptRecipeSaveResponseDto(Gpt gpt) {
        this.foodName = gpt.getFoodName();
        this.ingredient = gpt.getIngredient();
        this.context = gpt.getContext();
    }

    public static ChatGptRecipeSaveResponseDto createChatGptRecipeSaveResponseDto(Gpt gpt) {
        return new ChatGptRecipeSaveResponseDto(gpt);
    }
}
