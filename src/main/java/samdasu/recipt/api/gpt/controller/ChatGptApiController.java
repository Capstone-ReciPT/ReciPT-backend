package samdasu.recipt.api.gpt.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import samdasu.recipt.api.gpt.controller.dto.ResponseModel;
import samdasu.recipt.api.gpt.dto.chat.Message;
import samdasu.recipt.api.gpt.service.ChatGptService;
import samdasu.recipt.domain.entity.User;
import samdasu.recipt.domain.service.GptService;
import samdasu.recipt.domain.service.UserService;
import samdasu.recipt.security.config.auth.PrincipalDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static samdasu.recipt.api.gpt.constant.ChatConstants.*;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatGptApiController {

    private final ChatGptService chatgptService;

    private final GptService gptService;
    private final UserService userService;

    private List<Message> conversation = new ArrayList<>();

    @PostMapping("/send")
    public ResponseModel<String> sendContent(Authentication authentication, HttpServletRequest request, @RequestBody String content) {
        try {
            if (StringUtils.isEmpty(content)) {
                return ResponseModel.fail("Content is required.");
            }
            String requestId = UUID.randomUUID().toString();
            log.info("requestId {}, ip {}, send content: {}", requestId, request.getRemoteHost(), content);

            Message userMessage = new Message(ROLE_USER, content);

            if (CollectionUtils.isEmpty(conversation)) {
                Message systemMessage = new Message(ROLE_SYSTEM, JSON_SYSTEM_TASK_MESSAGE);
                conversation.add(systemMessage);
            }

            conversation.add(userMessage);
            String responseMessage = chatgptService.getResponse(conversation);
            log.info("requestId {}, ip {}\n get a reply:\n {}", requestId, request.getRemoteHost(), responseMessage);

            if (conversation.size() == 3) {
                String modifiedResponseMessage = firstConversation(userMessage, responseMessage);
                return ResponseModel.success(modifiedResponseMessage);
            }
            return ResponseModel.success(responseMessage);

        } catch (Exception e) {
            log.error("Error occurred during sendContent", e);
            return ResponseModel.fail("Error occurred during the request.");
        }
    }

    // 식재료로 처음 추천요리 응답 받을 때
    private static String firstConversation(Message userMessage, String responseMessage) throws JSONException {
        log.info("===========================================firstConversation===========================================");
        log.info("userMessage = {}", userMessage.getContent());

        String userInput = userMessage.getContent().strip();
        System.out.println("SUB 전 str:" + userInput);
        if (userInput.length() >= 2 && userInput.charAt(0) == '"' && userInput.charAt(userInput.length() - 1) == '"') {
            // 문자열이 큰따옴표로 둘러싸여 있는 경우에만 제거
            userInput = userInput.substring(1, userInput.length() - 1);
        }
        System.out.println("SUB 후 str:" + userInput);
        String[] userInputs = userInput.split(",");
        int totalLength = userInputs.length;

        // JSON 파싱
        JSONObject json = new JSONObject(responseMessage);
        JSONArray responseArray = json.getJSONArray("response");

        // GPT 응답값을 추천 요리에 필요한 사용자가 입력한 식재료, 퍼센트 수정
        for (int i = 0; i < responseArray.length(); i++) {
            JSONObject foodObj = responseArray.getJSONObject(i);
            String requiredIngredient = foodObj.getString("requiredIngredient");

            // 필요한 식재료에서 재료명과 개수를 추출
            String[] requiredIngredientsArray = requiredIngredient.split(", ");
            StringBuilder modifiedIngredients = new StringBuilder();
            int count = 0;

            log.info("userInput = {}", userInput);
            for (String ingredient : requiredIngredientsArray) {
                log.info("ingredient = {}", ingredient);
                String[] parts = ingredient.split(" ");
                // parts.length == 2일 경우에는 식재료 양도 명시, 1일 경우 식재료 이름만 표시.
                String ingredientName = parts[0];
                if (userInput.contains(ingredientName)) {
                    count++;
                    if (modifiedIngredients.length() > 0) {
                        modifiedIngredients.append(", ");
                    }
                    modifiedIngredients.append(ingredient);
                }
            }
            // 유저가 입력한 식재료 포함 비율
            double percent = (double) count / totalLength * 100;

            // 수정된 필요한 식재료 및 포함 비율로 다시 설정
            foodObj.put("requiredIngredient", modifiedIngredients.toString());
            foodObj.put("percent", String.format("%.1f%%", percent));
        }

        // 수정된 JSON 문자열 출력
        String modifiedResponseMessage = json.toString(4); // 4는 들여쓰기 수
        log.info("Modified responseMessage = {} ", modifiedResponseMessage);
        return modifiedResponseMessage;
    }

//    @PostMapping("/send")
//    public ResponseModel<String> sendContent(Authentication authentication, HttpServletRequest request, @RequestBody String content) {
//        try {
//            if (StringUtils.isEmpty(content)) {
//                return ResponseModel.fail("Content is required.");
//            }
//            String requestId = UUID.randomUUID().toString();
//            log.info("requestId {}, ip {}, send content: {}", requestId, request.getRemoteHost(), content);
//
//            Message userMessage = new Message(ROLE_USER, content);
//
//            if (CollectionUtils.isEmpty(conversation)) {
//                Message systemMessage = new Message(ROLE_SYSTEM, JSON_SYSTEM_TASK_MESSAGE);
//                conversation.add(systemMessage);
//            }
//
//            conversation.add(userMessage);
//            String responseMessage = chatgptService.getResponse(conversation);
//            log.info("requestId {}, ip {}\n get a reply:\n {}", requestId, request.getRemoteHost(), responseMessage);
//
//            if (conversation.size() == 3) {
//                System.out.println("============================================================");
//                System.out.println("responseMessage = " + responseMessage);
//                System.out.println("============================================================");
//                // 포함되는 식재료만 따로 추출?
//                System.out.println("!!userMessage = " + userMessage.getContent());
//                String str = userMessage.getContent().strip();
//                System.out.println("SUB 전 str:" + str);
//                if (str.length() >= 2 && str.charAt(0) == '"' && str.charAt(str.length() - 1) == '"') {
//                    // 문자열이 큰따옴표로 둘러싸여 있는 경우에만 제거
//                    str = str.substring(1, str.length() - 1);
//                }
//                System.out.println("SUB 후 str:" + str);
//                String[] split = str.split(",");
//                int totalLength = split.length;
//
//                // JSON 파싱
//                JSONObject json = new JSONObject(responseMessage);
//                JSONArray responseArray = json.getJSONArray("response");
//
//                // 각 요소에 대한 필요한 식재료 수정
//                for (int i = 0; i < responseArray.length(); i++) {
//                    JSONObject foodObj = responseArray.getJSONObject(i);
//                    String requiredIngredient = foodObj.getString("requiredIngredient");
//                    // 필요한 식재료에서 재료명과 개수를 추출
//                    String[] requiredIngredientsArray = requiredIngredient.split(", ");
//                    StringBuilder modifiedIngredients = new StringBuilder();
//                    int count = 0;
//                    for (String ingredient : requiredIngredientsArray) {
//                        System.out.println("str: " + str);
//                        System.out.println("ingredient!!: " + ingredient);
//                        String[] parts = ingredient.split(" ");
//                        // parts.length == 2일 경우에는 식재료양도 명시, 1일 경우 식재료 명만.
//                        String ingredientName = parts[0];
//                        if (str.contains(ingredientName)) {
//                            count++;
//                            if (modifiedIngredients.length() > 0) {
//                                modifiedIngredients.append(", ");
//                            }
//                            modifiedIngredients.append(ingredient);
//                        }
//                    }
//                    double percent = (double) count / totalLength * 100;
//
//                    // 수정된 필요한 식재료로 다시 설정
//                    foodObj.put("requiredIngredient", modifiedIngredients.toString());
//                    foodObj.put("percent", String.format("%.1f%%", percent));
//                }
//
//                // 수정된 JSON 문자열 출력
//                String modifiedResponseMessage = json.toString(4); // 4는 들여쓰기 수
//                System.out.println("Modified responseMessage = " + modifiedResponseMessage);
//
//                return ResponseModel.success(modifiedResponseMessage);
//            }
//            return ResponseModel.success(responseMessage);
//
//        } catch (Exception e) {
//            log.error("Error occurred during sendContent", e);
//            return ResponseModel.fail("Error occurred during the request.");
//        }
//    }

    /**
     * 유저가 입력한 식재료 포함 비율 반환
     */
//    @PostMapping("/send")
//    public ResponseModel<String> sendContent(Authentication authentication, HttpServletRequest request, @RequestBody String content) {
//        try {
//            if (StringUtils.isEmpty(content)) {
//                return ResponseModel.fail("Content is required.");
//            }
//            String requestId = UUID.randomUUID().toString();
//            log.info("requestId {}, ip {}, send content: {}", requestId, request.getRemoteHost(), content);
//
//            Message userMessage = new Message(ROLE_USER, content);
//
//            if (CollectionUtils.isEmpty(conversation)) {
//                Message systemMessage = new Message(ROLE_SYSTEM, JSON_SYSTEM_TASK_MESSAGE);
//                conversation.add(systemMessage);
//            }
//
//            conversation.add(userMessage);
//            String responseMessage = chatgptService.getResponse(conversation);
//            log.info("requestId {}, ip {}\n get a reply:\n {}", requestId, request.getRemoteHost(), responseMessage);
//
//            if (conversation.size() == 3) {
//                System.out.println("============================================================");
//                System.out.println("responseMessage = " + responseMessage);
//                System.out.println("============================================================");
//                // 포함되는 식재료만 따로 추출?
//                System.out.println("!!userMessage = " + userMessage.getContent());
//                String str = userMessage.getContent().strip();
//                System.out.println("SUB 전 str:" + str);
//                if (str.length() >= 2 && str.charAt(0) == '"' && str.charAt(str.length() - 1) == '"') {
//                    // 문자열이 큰따옴표로 둘러싸여 있는 경우에만 제거
//                    str = str.substring(1, str.length() - 1);
//                }
//                System.out.println("SUB 후 str:" + str);
//                String[] split = str.split(",");
//                int totalLength = split.length;
//
//                // JSON 파싱
//                JSONObject json = new JSONObject(responseMessage);
//                JSONArray responseArray = json.getJSONArray("response");
//
//                // 각 요소에 대한 필요한 식재료 수정
//                for (int i = 0; i < responseArray.length(); i++) {
//                    JSONObject foodObj = responseArray.getJSONObject(i);
//                    String requiredIngredient = foodObj.getString("requiredIngredient");
//
//                    // 필요한 식재료에서 재료명과 개수를 추출
//                    String[] requiredIngredientsArray = requiredIngredient.split(", ");
//                    StringBuilder modifiedIngredients = new StringBuilder();
//                    int count = 0;
//                    for (String ingredient : requiredIngredientsArray) {
//                        System.out.println("str: " + str);
//                        System.out.println("ingredient!!: " + ingredient);
//                        String[] parts = ingredient.split(" ");
//                        if (parts.length == 2) {
//                            System.out.println("=====================1번의 경우======================");
//                            String ingredientName = parts[0];
//                            if (str.contains(ingredientName)) {
//                                count++;
//                            }
//                        } else {
//                            System.out.println("=====================2번의 경우======================");
//                            String ingredientName = parts[0];
//                            if (str.contains(ingredientName)) {
//                                count++;
//                            }
//                        }
//                    }
//                    double percent = (double) count / totalLength * 100;
//                    modifiedIngredients.append(String.format("%.1f%%", percent));
//
//                    // 수정된 필요한 식재료로 다시 설정
//                    foodObj.put("requiredIngredient", modifiedIngredients.toString());
//                }
//
//                // 수정된 JSON 문자열 출력
//                String modifiedResponseMessage = json.toString(4); // 4는 들여쓰기 수
//                System.out.println("Modified responseMessage = " + modifiedResponseMessage);
//
//                return ResponseModel.success(modifiedResponseMessage);
//            }
//            return ResponseModel.success(responseMessage);
//
//        } catch (Exception e) {
//            log.error("Error occurred during sendContent", e);
//            return ResponseModel.fail("Error occurred during the request.");
//        }
//    }


    /**
     * 유저가 입력한 식재료 포함해 반환
     */
//    @PostMapping("/send")
//    public ResponseModel<String> sendContent(Authentication authentication, HttpServletRequest request, @RequestBody String content) {
//        try {
//            if (StringUtils.isEmpty(content)) {
//                return ResponseModel.fail("Content is required.");
//            }
//            String requestId = UUID.randomUUID().toString();
//            log.info("requestId {}, ip {}, send content: {}", requestId, request.getRemoteHost(), content);
//
//            Message userMessage = new Message(ROLE_USER, content);
//
//            if (CollectionUtils.isEmpty(conversation)) {
//                Message systemMessage = new Message(ROLE_SYSTEM, JSON_SYSTEM_TASK_MESSAGE);
//                conversation.add(systemMessage);
//            }
//
//            conversation.add(userMessage);
//            String responseMessage = chatgptService.getResponse(conversation);
//            log.info("requestId {}, ip {}\n get a reply:\n {}", requestId, request.getRemoteHost(), responseMessage);
//
//            if (conversation.size() == 3) {
//                System.out.println("============================================================");
//                System.out.println("responseMessage = " + responseMessage);
//                System.out.println("============================================================");
//                // 포함되는 식재료만 따로 추출?
//                System.out.println("!!userMessage = " + userMessage.getContent());
//                String str = userMessage.getContent().strip();
//                System.out.println("SUB 전 str:" + str);
//                if (str.length() >= 2 && str.charAt(0) == '"' && str.charAt(str.length() - 1) == '"') {
//                    // 문자열이 큰따옴표로 둘러싸여 있는 경우에만 제거
//                    str = str.substring(1, str.length() - 1);
//                }
//                System.out.println("SUB 후 str:" + str);
//
//                // JSON 파싱
//                JSONObject json = new JSONObject(responseMessage);
//                JSONArray responseArray = json.getJSONArray("response");
//
//                // 각 요소에 대한 필요한 식재료 수정
//                for (int i = 0; i < responseArray.length(); i++) {
//                    JSONObject foodObj = responseArray.getJSONObject(i);
//                    String requiredIngredient = foodObj.getString("requiredIngredient");
//
//                    // 필요한 식재료에서 재료명과 개수를 추출
//                    String[] requiredIngredientsArray = requiredIngredient.split(", ");
//                    StringBuilder modifiedIngredients = new StringBuilder();
//
//                    for (String ingredient : requiredIngredientsArray) {
//                        // 재료명과 개수를 분리
//                        String[] parts = ingredient.split(" ");
//                        if (parts.length == 2) {
//                            String ingredientName = parts[0];
//                            if (str.contains(ingredientName)) {
//                                if (modifiedIngredients.length() > 0) {
//                                    modifiedIngredients.append(", ");
//                                }
//                                modifiedIngredients.append(ingredient);
//                            }
//                        }
//                    }
//
//                    // 수정된 필요한 식재료로 다시 설정
//                    foodObj.put("requiredIngredient", modifiedIngredients.toString());
//                }
//
//                // 수정된 JSON 문자열 출력
//                String modifiedResponseMessage = json.toString(4); // 4는 들여쓰기 수
//                System.out.println("Modified responseMessage = " + modifiedResponseMessage);
//
//                return ResponseModel.success(modifiedResponseMessage);
//            }
//            return ResponseModel.success(responseMessage);
//
//        } catch (Exception e) {
//            log.error("Error occurred during sendContent", e);
//            return ResponseModel.fail("Error occurred during the request.");
//        }
//    }
    @PostMapping("/search")
    public ResponseModel<String> searchFoodRecipe(Authentication authentication, HttpServletRequest request, @RequestBody String content) {
        try {
            if (StringUtils.isEmpty(content)) {
                return ResponseModel.fail("Content is required.");
            }
            String requestId = UUID.randomUUID().toString();
            log.info("requestId {}, ip {}, send content: {}", requestId, request.getRemoteHost(), content);

            Message userMessage = new Message(ROLE_USER, content);

            if (CollectionUtils.isEmpty(conversation)) {
                Message systemMessage = new Message(ROLE_SYSTEM, JSON_SEARCHBAR_SYSTEM_TASK_MESSAGE);
                conversation.add(systemMessage);
            }
            conversation.add(userMessage);

            String responseMessage = chatgptService.getResponse(conversation);
            log.info("requestId {}, ip {}\n get a reply:\n {}", requestId, request.getRemoteHost(), responseMessage);

            return ResponseModel.success(responseMessage);
        } catch (Exception e) {
            log.error("Error occurred during searchFoodRecipe", e);
            return ResponseModel.fail("Error occurred during the request.");
        }
    }

    @PostMapping("/recommend")
    public ResponseModel<String> sendChat(Authentication authentication, HttpServletRequest request, @RequestBody String content) {
        try {
            if (StringUtils.isEmpty(content)) {
                return ResponseModel.fail("Content is required.");
            }
            String requestId = UUID.randomUUID().toString();
            log.info("requestId {}, ip {}, send content: {}", requestId, request.getRemoteHost(), content);

            Message userMessage = new Message(ROLE_USER, content);

            if (CollectionUtils.isEmpty(conversation)) {
                Message systemMessage = new Message(ROLE_SYSTEM, RECOMMEND_SYSTEM_TASK_MESSAGE);
                conversation.add(systemMessage);
            }

            conversation.add(userMessage);

            String responseMessage = chatgptService.getResponseV2(conversation);
            log.info("requestId {}, ip {}\n get a reply:\n {}", requestId, request.getRemoteHost(), responseMessage);

            return ResponseModel.success(responseMessage);
        } catch (Exception e) {
            log.error("Error occurred during sendContent", e);
            return ResponseModel.fail("Error occurred during the request.");
        }
    }

    @PostMapping("/refresh")
    public ResponseModel<String> refreshConversation() {
        clearConversation();
        return ResponseModel.success("Conversation refreshed successfully.");
    }

    @PostMapping("/edit")
    public ResponseModel<String> editGptRecipe(Authentication authentication, @RequestBody String content) {
        try {
            if (StringUtils.isEmpty(content)) {
                return ResponseModel.fail("Content is required.");
            }
            Message userMessage = new Message(ROLE_USER, EDIT_COMMAND_MESSAGE + content);
            conversation.add(userMessage);
            String responseMessage = chatgptService.getResponse(conversation);
            return ResponseModel.success(responseMessage);
        } catch (Exception e) {
            log.error("Error occurred during editGptRecipe", e);
            return ResponseModel.fail("Error occurred during the request.");
        }
    }

    @PostMapping("/save")
    public ResponseModel<String> saveGptRecipe(Authentication authentication) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        User findUser = userService.findById(principal.getUser().getUserId());
        try {
            Message userMessage = new Message(ROLE_USER, SAVE_COMMAND_MESSAGE);
            conversation.add(userMessage);
            String responseMessage = chatgptService.getResponse(conversation);

            int startIndex = responseMessage.indexOf("{");
            int endIndex = responseMessage.lastIndexOf("}");

            if (startIndex >= 0) {
                if (endIndex == -1 || endIndex < startIndex) {
                    endIndex = responseMessage.length();
                }

                String jsonResponse = responseMessage.substring(startIndex, endIndex + 1);

//                Long gptRecipeId = saveGptResponse(jsonResponse, 2L);
                Long gptRecipeId = saveGptResponse(jsonResponse, findUser.getUserId());
                clearConversation();
                log.info("Saved GptRecipe. foodName: {}", gptService.getGptRecipeByGptId(gptRecipeId).getFoodName());
            }
//            Pattern pattern1 = Pattern.compile("\"\\{.*\\}\"");
//            Pattern pattern2 = Pattern.compile("\\{.*\\}");
//            Matcher matcher1 = pattern1.matcher(responseMessage);
//            Matcher matcher2 = pattern2.matcher(responseMessage);
//            if (matcher1.find()) {
//                String jsonString = matcher1.group();
//
//                try {
//                    Long gptRecipeId = saveGptPrompt(jsonString, userResponseDto.getUserId());
//                    clearConversation();
//                    log.info("Saved GptRecipe. foodName: {}", gptService.getGptRecipeByGptId(gptRecipeId).getFoodName());
//                } catch (IOException e) {
//                    log.error("Failed to extract values from JSON response", e);
//                    return ResponseModel.fail("Failed to extract values from JSON response");
//                }
//            } else if (matcher2.find()) {
//                String jsonString = matcher2.group();
//
//                try {
//                    Long gptRecipeId = saveGptPrompt(jsonString, userResponseDto.getUserId());
//                    clearConversation();
//                    log.info("Saved GptRecipe. foodName: {}", gptService.getGptRecipeByGptId(gptRecipeId).getFoodName());
//                } catch (IOException e) {
//                    log.error("Failed to extract values from JSON response", e);
//                    return ResponseModel.fail("Failed to extract values from JSON response");
//                }
//
//            }
            return ResponseModel.success("successfully saved the recipe!");
        } catch (Exception e) {
            log.error("Error occurred during saveGptRecipe", e);
            return ResponseModel.fail("Error occurred during the request.");
        }
    }

    private Long saveGptResponse(String jsonResponse, Long userId) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseJson = objectMapper.readTree(jsonResponse);
        String foodName = responseJson.path("foodName").asText();
        String ingredient = responseJson.path("ingredient").asText();
        String context = responseJson.path("context").asText();

        if (StringUtils.isEmpty(foodName) || StringUtils.isEmpty(ingredient) || StringUtils.isEmpty(context)) {
            log.error("Failed to extract values from JSON response. Missing required fields.");
            throw new IllegalArgumentException("Missing required fields in JSON response");
        }

        Long gptRecipeId = gptService.createGptRecipe(foodName, ingredient, context, userId);
        return gptRecipeId;
    }

//    private Long saveGptPrompt(String jsonString, Long userId) throws JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode responseJson = objectMapper.readTree(jsonString);
//        String foodName = responseJson.path("foodName").asText();
//        String ingredient = responseJson.path("ingredient").asText();
//        String context = responseJson.path("context").asText();
//        Long gptRecipeId = gptService.createGptRecipe(foodName, ingredient, context, userId);
//        return gptRecipeId;
//    }

    private void clearConversation() {
        conversation.clear();
    }

}