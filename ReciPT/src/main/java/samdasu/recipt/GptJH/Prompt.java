package samdasu.recipt.GptJH;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class Prompt {

    private static OpenAiService openAiService;

    @Value("${openai.key}")
    private String apiKey;

    @Value("${openai.timeout}")
    private int apiTimeout;

    private static final String GPT_MODEL = "gpt-3.5-turbo";
    //이제부터 넌 몇가지 식재료로 요리를 추천하고 그 요리의 레시피를 만들어주는 chefGPT야.
//응답 형식은 다음과 같아.
//1. 추천해준 음식이름
//2. 그 음식을 만드는 레시피의 재료
//3. 레시피
//모든 응답은 한국어로 부탁해.
    private static final String SYSTEM_TASK_MESSAGE = //JSON 형식
            "From now on, you are a chefGPT who recommends a dish with a few ingredients and creates a recipe for that dish. Your rules are as follows\n" +
                    "\n" +
                    "1. give me the ingredients and recommend one dish that can be made from those ingredients.\n" +
                    "2. give me the ingredients for the recipe to make that dish and describe the process in detail, breaking it down into steps 1, 2, and so on.\n" +
                    "3. specify the exact quantities in the recipe, e.g. 300 ml of water\n" +
                    "4. and don't separate all these conditions n times, but say them together\n" +
                    "You are an API server that responds in a JSON format.\n" +
                    "Don't say anything else. Respond only with the JSON.\n" +
                    "Respond in a JSON format, including an array named 'recipes'. Each item of the array is another JSON object that includes 'foodName' as a text, the content of which is the name of the food you said in response." +
                    "'ingredients' as a text, which is the ingredient you said in response. Context will also respond with text, which will be the recipe you said in response.\n" +
                    "Don't add anything else in the end after you respond with the JSON." +
                    "Finally, all answers should be in Korean.";


    @PostConstruct
    public void initGptService() {
        openAiService = new OpenAiService(apiKey, Duration.ofSeconds(40)); //40초 안에 응답값 줌! 아니면 에러 발생
        System.out.println("Connected to OpenAI!");
    }

//    "Your rules are as follows\n" +
//            "1. give me the ingredients and recommend one dish that can be made from those %s.\n" +
//            "2. give me the ingredients for the recipe to make that dish and describe the process in detail, breaking it down into steps 1, 2, and so on.\n" +
//            "3. specify the exact quantities in the recipe, e.g. 300 ml of water\n" +
//            "4. additionally, %s will be given.\n" +
//            "5. if there are no instructions, follow the rules in 1,2,3.\n" +
//            "6. if a command starts with \"question\", it's a question about a recipe you've recently answered, so consider that recipe when answering.\n" +
//            "7. if the command starts with \"modify\", it is a request to modify a recipe you have recently answered. Please answer the modified recipe.\n" +
//            "8. if the command starts with \"end\", it means that you shouldn't answer the question by referring to the previous recipe. Assume that the customer request is over and a new customer has come.\n" +
//            "9. And don't separate all these conditions into n number of times, but say them together\n"

//이제부터 넌 몇가지 식재료로 요리를 추천하고 그 요리의 레시피를 만들어주는 chefGPT야. 너가 이제부터 해야할 규칙은 다음과 같아
//
//1. 식재료를 말해주면 그 식재료들을 중심으로 만들 수 있는 음식을 하나만 추천해줘
//2. 그 음식을 만드는 레시피의 재료를 알려주고, 진행과정은 1번, 2번 이런식으로 구분해서 자세하게 설명해줘
//3. 레시피에서 양을 정확히 명시해줘 ex) 물 300ml
//4. 추가적으로, "질문"으로 시작하는 문장은 최근에 너가 대답한 레시피에 대한 질문이니 그 레시피를 고려해서 대답해줘
//5. "수정"으로 시작하는 문장은 최근에 너가 대답한 레시피를 수정하는 요청이야. 수정된 레시피를 답변해줘
//6. 그리고 이 모든 조건을 n번으로 구분짓지 말고 같이 말해줘
//7. “종료” 단어가 제시되면 이전의 레시피를 참고해서 답변하지말라는 뜻이야. 고객 요청이 끝나고, 새로운 고객이 온거라고 생각해.


    public RecommendFoodResponse recommendFood(String ingredients, String commend) {
        String prompt = String.format("Here are the %s I have and here are the %s."
                , ingredients, commend);

        /**
         * db내용 findby(foodname, ingredients, context) 같은 내용 찾기
         * if(같은 내용 없으면){
         *      new RecommendFoodResponse()
         *      response.setRecommendFoodResponse(recommendFoods(jsonResponse));
         *      return response;
         *  }
         */

        try {
            //gpt한테 보내는 request
            ChatCompletionRequest request = new ChatCompletionRequest().builder()
                    .model(GPT_MODEL)
                    .temperature(0.8) //0~1사이, 높을수록 창의적인 결과, 낮을수록 예측 가능한 결과
                    .messages(List.of(
                            new ChatMessage("system", SYSTEM_TASK_MESSAGE),
                            new ChatMessage("user", prompt)))
                    .build();
            //gpt한테 request 보내기
            StringBuilder builder = new StringBuilder();

            openAiService.createChatCompletion(request).getChoices().forEach(choice -> {
                builder.append(choice.getMessage().getContent());
            });
            String jsonResponse = builder.toString();

            System.out.println(jsonResponse);

//            List<RecommendFood> recommendFoodResponses = makeJsonForm(jsonResponse);
//            db.save(new GptService(foodName, ingredients, context));

            //Json으로 변환
            RecommendFoodResponse response = new RecommendFoodResponse();
            response.setRecommendsFood(makeJsonForm(jsonResponse)); //음식 여러개
            return response;

        } catch (Exception e) {
            e.printStackTrace();

            RecommendFoodResponse response = new RecommendFoodResponse();
            response.setError(e.getMessage());

            return response;
        }
    }

    /**
     * 음식 여러개 추천
     */
    private List<RecommendFood> makeJsonForm(String json) throws JSONException {
        JSONObject jsonResponse = new JSONObject(json);
        JSONArray recipes = jsonResponse.getJSONArray("recipes");

        List<RecommendFood> foodList = new ArrayList<>(recipes.length());

        for (int i = 0; i < recipes.length(); i++) {
            JSONObject food = recipes.getJSONObject(i);

            RecommendFood foo = new RecommendFood(
                    food.getString("foodName"),
                    food.getString("ingredients"),
                    food.getString("context"));

            System.out.println("foo = " + foo);
            foodList.add(foo);
        }
        return foodList;
    }
}
