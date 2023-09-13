package samdasu.recipt.domain.controller.dto.Gpt;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GptResponseDto {

//    private String foodName;

//    private String ingredient;

    //    private String context;
    private String responseMessage;

    //    public GptResponseDto(String foodName, String ingredient, String context) {
//        this.foodName = foodName;
//        this.ingredient = ingredient;
//        this.context = context;
//    }
    public GptResponseDto(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    //    public static GptResponseDto createGptResponseDto(String foodName, String ingredient, String context) {
//        return new GptResponseDto(foodName, ingredient, context);
//    }
    public static GptResponseDto createGptResponseDto(String responseMessage) {
        return new GptResponseDto(responseMessage);
    }
}
