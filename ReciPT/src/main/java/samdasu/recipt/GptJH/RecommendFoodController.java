package samdasu.recipt.GptJH;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recommend-food")
public class RecommendFoodController {

    @Autowired
    private RecommendFoodGptPrompt gptService;

    @PostMapping
    public RecommendFoodResponse recommendFood(@RequestParam("ingredients") String ingredients) {
        return gptService.recommendFood(ingredients);
    }

}