package samdasu.recipt.api.gpt.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import samdasu.recipt.api.gpt.controller.dto.ChatGptRecipeSaveResponseDto;
import samdasu.recipt.api.gpt.controller.dto.ResponseModel;
import samdasu.recipt.api.gpt.controller.dto.chat.Message;
import samdasu.recipt.api.gpt.service.ChatGptService;
import samdasu.recipt.domain.entity.Gpt;
import samdasu.recipt.domain.entity.User;
import samdasu.recipt.domain.service.GptService;
import samdasu.recipt.domain.service.UserService;
import samdasu.recipt.security.config.auth.PrincipalDetails;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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
//    @PostMapping("/fridge-recipes")
    public ResponseModel<String> getFridgeRecipes(Authentication authentication, HttpServletRequest request, @RequestBody String userContent) {
        return processMessage(authentication, request, userContent, DIGGING_OUT_THE_FRIDGE_JSON_SYSTEM_TASK_MESSAGE, true);
    }

    @PostMapping("/search")
//    @PostMapping("/unrecorded")
    public ResponseModel<String> searchUnrecordedRecipe(Authentication authentication, HttpServletRequest request, @RequestBody String userContent) {
        return processMessage(authentication, request, userContent, SEARCHBAR_JSON_SYSTEM_TASK_MESSAGE, false);
    }

    @PostMapping("/recommend")
//    @PostMapping("/suggest-today-menu")
    public ResponseModel<String> suggestTodayMenu(Authentication authentication, HttpServletRequest request, @RequestBody String userContent) {
        return processMessage(authentication, request, userContent, SUGGEST_TODAY_MENU_SYSTEM_TASK_MESSAGE, false);
    }


    @PostMapping("/refresh")
//    @PostMapping("/refresh-conversation")
    public ResponseModel<String> refreshConversation() {
        clearConversation();
        return ResponseModel.success("Conversation refreshed successfully.");
    }

    @PostMapping("/edit")
//    @PostMapping("/edit-gpt-recipe")
    public ResponseModel<String> editGptRecipe(Authentication authentication, @RequestBody String userContent) {
        try {
            if (StringUtils.isEmpty(userContent)) {
                return ResponseModel.fail("Content is required.");
            }
            Message userMessage = new Message(ROLE_USER, EDIT_COMMAND_MESSAGE + userContent);
            conversation.add(userMessage);
            String responseMessage = chatgptService.getResponse(conversation);
            return ResponseModel.success(responseMessage);
        } catch (Exception e) {
            log.error("Error occurred during editGptRecipe", e);
            return ResponseModel.fail("Error occurred during the request.");
        }
    }
    @PostMapping("/save")
    //    @PostMapping("/save-gpt-recipe")
    public ResponseModel<ChatGptRecipeSaveResponseDto> saveGptRecipe(Authentication authentication) {
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

                // JSON 파싱 및 필드 유효성 검사
                ChatGptRecipeSaveResponseDto chatGptRecipeSaveResponseDto = parseAndValidateGptResponse(jsonResponse, findUser.getUserId());
                log.info("Saved GptRecipe. foodName: {}", chatGptRecipeSaveResponseDto.getFoodName());
                clearConversation();

                return ResponseModel.success(chatGptRecipeSaveResponseDto);
            } else {
                throw new IOException("Error occurred caused Incorrect JSON format");
            }
        } catch (Exception e) {
            log.error("Error occurred during saveGptRecipe", e);
            return ResponseModel.fail(null);
        }
    }

    private ResponseModel<String> processMessage(Authentication authentication, HttpServletRequest request, String userContent, String messageType, boolean isDiggingOut) {
        try {
            if (StringUtils.isEmpty(userContent)) {
                return ResponseModel.fail("Content is required.");
            }
            String requestId = UUID.randomUUID().toString();
            log.info("requestId {}, ip {}, send content: {}", requestId, request.getRemoteHost(), userContent);

            Message userMessage = new Message(ROLE_USER, userContent);

            addSystemMessage(messageType);

            conversation.add(userMessage);
            String responseMessage = chatgptService.getResponse(conversation);
            log.info("requestId {}, ip {}\n get a reply:\n {}", requestId, request.getRemoteHost(), responseMessage);

            if (isDiggingOut && conversation.size() == 3) {
                try {
                    String modifiedResponseMessage = firstConversation(userMessage, responseMessage);
                    return ResponseModel.success(modifiedResponseMessage);
                } catch (IOException e) {
                    log.error("Error occurred during processing message", e);
                    return ResponseModel.fail("error occurred during processing: ");
                }
            }

            return ResponseModel.success(responseMessage);
        } catch (Exception e) {
            log.error("Error occurred during processing message", e);
            return ResponseModel.fail("Error occurred during the request.");
        }
    }

    private static String firstConversation(Message userMessage, String responseMessage) throws IOException {
        log.info("===========================================firstConversation===========================================");
        log.info("userMessage = {}", userMessage.getContent());

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseMessage);

            String userInput = userMessage.getContent().strip();
            if (userInput.length() >= 2 && userInput.charAt(0) == '"' && userInput.charAt(userInput.length() - 1) == '"') {
                // 문자열이 큰따옴표로 둘러싸여 있는 경우에만 제거
                userInput = userInput.substring(1, userInput.length() - 1);
            }
            String[] userInputs = userInput.split(",");
            int totalLength = userInputs.length;

            // GPT 응답값을 추천 요리에 필요한 사용자가 입력한 식재료, 퍼센트 수정
            JsonNode responseArray = jsonNode.get("response");
            for (JsonNode foodNode : responseArray) {
                String requiredIngredient = foodNode.get("requiredIngredient").asText();

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
                ((ObjectNode) foodNode).put("requiredIngredient", modifiedIngredients.toString());
                ((ObjectNode) foodNode).put("percent", String.format("%.1f%%", percent));
            }

            // 수정된 JSON 문자열 출력
            String modifiedResponseMessage = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
            log.info("Modified responseMessage = {} ", modifiedResponseMessage);
            return modifiedResponseMessage;

        } catch (IOException e) {
            log.error("Error occurred during JSON processing", e);
            throw new IOException("Error occurred during JSON processing", e);
        }
    }

    private void addSystemMessage(String messageType) {
        if (CollectionUtils.isEmpty(conversation)) {
            Message systemMessage = new Message(ROLE_SYSTEM, messageType);
            conversation.add(systemMessage);
        }
    }

    private ChatGptRecipeSaveResponseDto parseAndValidateGptResponse(String jsonResponse, Long userId) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode responseJson = objectMapper.readTree(jsonResponse);
            String foodName = responseJson.path("foodName").asText();
            String ingredient = responseJson.path("ingredient").asText();
            String context = responseJson.path("context").asText();

            if (StringUtils.isAnyEmpty(foodName, ingredient, context)) {
                log.error("Failed to extract values from JSON response. Missing required fields.");
                throw new IllegalArgumentException("Missing required fields in JSON response");
            }

            Long gptRecipeId = gptService.createGptRecipe(foodName, ingredient, context, userId);
            Gpt findGptRecipe = gptService.getGptRecipeByGptId(gptRecipeId);

            return ChatGptRecipeSaveResponseDto.createChatGptRecipeSaveResponseDto(findGptRecipe);
        } catch (IOException e) {
            log.error("Error occurred during JSON parsing", e);
            return ChatGptRecipeSaveResponseDto.createChatGptRecipeSaveResponseDto(null);
        }
    }

    private void clearConversation() {
        conversation.clear();
    }
}