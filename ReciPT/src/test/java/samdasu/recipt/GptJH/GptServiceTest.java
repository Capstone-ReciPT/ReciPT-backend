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
    GptService gptService;

    @Test
    public void gpt_response() throws Exception {
        //given
        String ingredients = "계란,당근,쪽파";
        String commend = "";

        //when
        RecommendFoodResponse recommendFoodResponse = gptService.recommendFood(ingredients, commend);

        //then

    }

    @Test
    public void gpt_response_question() throws Exception {
        //given
        String ingredients = "계란,당근,쪽파";
        String commend = "question, 다진 당근 대체 재료로 뭘 쓰면 좋을까?";

        //when
        RecommendFoodResponse recommendFoodResponse = gptService.recommendFood(ingredients, commend);

        //then
    }

    @Test
    public void gpt_response_fix() throws Exception {
        //given
        String ingredients = "계란,당근,쪽파";
        String commend = "modify, 다진 당근을 다진 목이버섯으로 바꿀게";

        //when
        RecommendFoodResponse recommendFoodResponse = gptService.recommendFood(ingredients, commend);

        //then
    }

    @Test
    public void gpt_response_end() throws Exception {
        //given
        String ingredients = "계란,당근,쪽파";
        String commend = "end";

        //when
        RecommendFoodResponse recommendFoodResponse = gptService.recommendFood(ingredients, commend);

        //then
    }
}