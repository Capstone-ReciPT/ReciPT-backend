package samdasu.recipt.GptJH;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
class GptServiceTest {
    @Autowired
    RecommendFoodGptPrompt gptService;

    @Test
    public void gpt_response() throws Exception {
        //given
        String ingredients = "계란,당근,쪽파";

        //when
        RecommendFoodResponse recommendFoodResponse = gptService.recommendFood(ingredients);

        //then
    }
}