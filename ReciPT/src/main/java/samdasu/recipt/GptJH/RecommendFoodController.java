package samdasu.recipt.GptJH;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//@RestController
//@RequestMapping("/recommend-food")
//public class RecommendFoodController {
//
//    @Autowired
//    private GptService gptService;
//
//    @GetMapping
//    public RecommendFoodResponse recommendFood(
//            @RequestParam("ingredients") String ingredients) {
//
//        return gptService.recommendFood(ingredients);
//    }
//}

@RestController
@RequestMapping("/recommend-food")
public class RecommendFoodController {

    @Autowired
    private Prompt gptService;

    @GetMapping
    public String showForm() {
        return "<form method=\"post\" action=\"/recommend-food\">" +
                "  <label for=\"ingredients\">Ingredients:</label><br>" +
                "  <input type=\"text\" id=\"ingredients\" name=\"ingredients\"><br>" +
                "  <input type=\"submit\" value=\"GPT 출력하기\">" +
                "</form>";
    }

    @PostMapping
    public RecommendFoodResponse recommendFood(@RequestParam("ingredients") String ingredients, @RequestParam("commend") String commend) {
        return gptService.recommendFood(ingredients, commend);
    }

}