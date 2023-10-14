package samdasu.recipt.domain.controller.dto.Gpt;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.domain.entity.Gpt;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class GptShortResponseDto {

    private Long gptId;
    private String foodName;
    private String  createdDate;

    public GptShortResponseDto(Gpt gpt) {
        this.gptId = gpt.getGptId();
        this.foodName = gpt.getFoodName();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.createdDate = gpt.getCreateDate().format(formatter);
    }

    public static GptShortResponseDto createGptShortResponseDto(Gpt gpt) {
        return new GptShortResponseDto(gpt);
    }
}
