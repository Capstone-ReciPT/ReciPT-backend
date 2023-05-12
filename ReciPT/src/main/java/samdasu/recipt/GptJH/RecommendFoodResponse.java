package samdasu.recipt.GptJH;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RecommendFoodResponse {
    private List<RecommendFood> recommendsFood; //음식 여러개
    private String error;

    public RecommendFoodResponse() {
    }
}
