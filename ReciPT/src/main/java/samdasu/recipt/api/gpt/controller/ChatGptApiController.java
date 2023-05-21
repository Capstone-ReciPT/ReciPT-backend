package samdasu.recipt.api.gpt.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import samdasu.recipt.api.gpt.controller.dto.ResponseModel;
import samdasu.recipt.api.gpt.dto.chat.Message;
import samdasu.recipt.api.gpt.service.ChatGptService;
import samdasu.recipt.domain.controller.dto.User.UserResponseDto;
import samdasu.recipt.domain.service.GptService;

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

    private List<Message> conversation = new ArrayList<>();

    @PostMapping("/send")
    public ResponseModel<String> sendContent(@AuthenticationPrincipal UserResponseDto userResponseDto, HttpServletRequest request, @RequestBody String content) {
        try {
            if (StringUtils.isEmpty(content)) {
                return ResponseModel.fail("Content is required.");
            }
            String requestId = UUID.randomUUID().toString();
            log.info("requestId {}, ip {}, send content: {}", requestId, request.getRemoteHost(), content);

            Message userMessage = new Message(USER, content);

            if (CollectionUtils.isEmpty(conversation)) {
                Message systemMessage = new Message(SYSTEM, SYSTEM_TASK_MESSAGE);
                conversation.add(systemMessage);
            }

            conversation.add(userMessage);

            String responseMessage = chatgptService.getResponse(conversation);
            log.info("requestId {}, ip {}\n get a reply:\n {}", requestId, request.getRemoteHost(), responseMessage);

            return ResponseModel.success(responseMessage);
        } catch (Exception e) {
            log.error("Error occurred during sendContent", e);
            return ResponseModel.fail("Error occurred during the request.");
        }
    }

    @PostMapping("/search")
    public ResponseModel<String> searchFoodRecipe(@AuthenticationPrincipal UserResponseDto userResponseDto, HttpServletRequest request, @RequestBody String content) {
        try {
            if (StringUtils.isEmpty(content)) {
                return ResponseModel.fail("Content is required.");
            }
            String requestId = UUID.randomUUID().toString();
            log.info("requestId {}, ip {}, send content: {}", requestId, request.getRemoteHost(), content);

            Message userMessage = new Message(USER, content);

            if (CollectionUtils.isEmpty(conversation)) {
                Message systemMessage = new Message(SYSTEM, SEARCHBAR_SYSTEM_TASK_MESSAGE);
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

    @PostMapping("/refresh")
    public ResponseModel<String> refreshConversation() {
        clearConversation();
        return ResponseModel.success("Conversation refreshed successfully.");
    }

    @PostMapping("/edit")
    public ResponseModel<String> editGptRecipe(@AuthenticationPrincipal UserResponseDto userResponseDto, @RequestBody String content) {
        try {
            if (StringUtils.isEmpty(content)) {
                return ResponseModel.fail("Content is required.");
            }
            Message userMessage = new Message(USER, "[수정] " + content);
            conversation.add(userMessage);
            String responseMessage = chatgptService.getResponse(conversation);
            return ResponseModel.success(responseMessage);
        } catch (Exception e) {
            log.error("Error occurred during editGptRecipe", e);
            return ResponseModel.fail("Error occurred during the request.");
        }
    }

    @PostMapping("/save")
    public ResponseModel<String> saveGptRecipe(@AuthenticationPrincipal UserResponseDto userResponseDto) {
        try {
            Message userMessage = new Message(USER, SAVE_APPEND_MESSAGE + "[저장]");
            conversation.add(userMessage);
            String responseMessage = chatgptService.getResponse(conversation);

            int startIndex = responseMessage.indexOf("{");
            int endIndex = responseMessage.lastIndexOf("}");

            if (startIndex >= 0) {
                if (endIndex == -1 || endIndex < startIndex) {
                    endIndex = responseMessage.length();
                }

                String jsonResponse = responseMessage.substring(startIndex, endIndex + 1);

                Long gptRecipeId = saveGptPrompt_V2(jsonResponse, userResponseDto);
//                Long gptRecipeId = gptService.createGptRecipe(foodName, ingredient, context, 2L);
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
            return ResponseModel.success(responseMessage);
        } catch (Exception e) {
            log.error("Error occurred during saveGptRecipe", e);
            return ResponseModel.fail("Error occurred during the request.");
        }
    }

    private Long saveGptPrompt_V2(String jsonResponse, UserResponseDto userResponseDto) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseJson = objectMapper.readTree(jsonResponse);
        String foodName = responseJson.path("foodName").asText();
        String ingredient = responseJson.path("ingredient").asText();
        String context = responseJson.path("context").asText();

        if (StringUtils.isEmpty(foodName) || StringUtils.isEmpty(ingredient) || StringUtils.isEmpty(context)) {
            log.error("Failed to extract values from JSON response. Missing required fields.");
            throw new IllegalArgumentException("Missing required fields in JSON response");
        }

        Long gptRecipeId = gptService.createGptRecipe(foodName, ingredient, context, userResponseDto.getUserId());
        return gptRecipeId;
    }

    private Long saveGptPrompt(String jsonString, Long userId) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseJson = objectMapper.readTree(jsonString);
        String foodName = responseJson.path("foodName").asText();
        String ingredient = responseJson.path("ingredient").asText();
        String context = responseJson.path("context").asText();
        Long gptRecipeId = gptService.createGptRecipe(foodName, ingredient, context, userId);
        return gptRecipeId;
    }

    private void clearConversation() {
        conversation.clear();
    }

}