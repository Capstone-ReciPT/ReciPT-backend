package samdasu.recipt.api.gpt.controller.dto.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseChoice {
    private Message message;

    @JsonProperty("finish_reason")
    private String finishReason;

    private Integer index;
}
