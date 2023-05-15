package samdasu.recipt.GptJH.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import samdasu.recipt.GptJH.dto.Usage;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponse {
    private String id;
    private String object;
    private LocalDate created;
    private String model;
    private List<ResponseChoice> choices;
    private Usage usage;
}
