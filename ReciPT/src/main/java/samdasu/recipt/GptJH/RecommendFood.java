package samdasu.recipt.GptJH;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecommendFood {
    private String foodName;
    private String ingredients;
    private String context;
}
