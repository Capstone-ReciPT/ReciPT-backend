package samdasu.recipt.api.gpt.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import samdasu.recipt.api.gpt.controller.dto.ResponseModel;
import samdasu.recipt.api.gpt.dto.chat.Message;
import samdasu.recipt.api.gpt.service.ChatGptService;
import samdasu.recipt.domain.controller.dto.Gpt.GptResponseDto;
import samdasu.recipt.domain.controller.dto.User.UserResponseDto;
import samdasu.recipt.domain.service.GptService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static samdasu.recipt.api.gpt.constant.ChatConstants.*;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
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

//            if (isSaveChat(content)) {
//                Pattern pattern = Pattern.compile("\\{.*\\}");
//                Matcher matcher = pattern.matcher(responseMessage);
//                if (matcher.find()) {
//                    String jsonString = matcher.group();
//
//                    try {
//                        Long gptRecipeId = saveGptPrompt(jsonString, userResponseDto.getUserId());
//                        clearConversation();
//                        log.info("Saved GptRecipe. foodName: {}", gptService.getGptRecipeByGptId(gptRecipeId).getFoodName());
//                    } catch (IOException e) {
//                        log.error("Failed to extract values from JSON response", e);
//                        return ResponseModel.fail("Failed to extract values from JSON response");
//                    }
//                }
//            }

            gptResponse(userResponseDto, request, responseMessage);

            return ResponseModel.success(responseMessage);
        } catch (Exception e) {
            log.error("Error occurred during sendContent", e);
            return ResponseModel.fail("Error occurred during the request.");
        }
    }

    /**
     * 저장 버튼 클릭
     */
    @PostMapping("/save")
    public ResponseModel<String> saveResponse(@AuthenticationPrincipal UserResponseDto userResponseDto) {
        conversation.add(new Message(USER, "[저장]"));
        String responseMessage = chatgptService.getResponse(conversation);
        Pattern pattern = Pattern.compile("\\{.*\\}");
        Matcher matcher = pattern.matcher(responseMessage);
        if (matcher.find()) {
            String jsonString = matcher.group();

            try {
                Long gptRecipeId = saveGptPrompt(jsonString, userResponseDto.getUserId());
                clearConversation();
                log.info("Saved GptRecipe. foodName: {}", gptService.getGptRecipeByGptId(gptRecipeId).getFoodName());
            } catch (IOException e) {
                log.error("Failed to extract values from JSON response", e);
                return ResponseModel.fail("Failed to extract values from JSON response");
            }
        }
        return ResponseModel.success(responseMessage);
    }

    /**
     * gpt 응답 내용 플러터에 보이기
     */
    @GetMapping
    public Result gptResponse(@AuthenticationPrincipal UserResponseDto userResponseDto, HttpServletRequest request, String responseMessage) {
        String requestId = UUID.randomUUID().toString();

        log.info("requestId {}, ip {}\n in flutter:\n {}", requestId, request.getRemoteHost(), responseMessage);
        GptResponseDto gptResponseDto = GptResponseDto.createGptResponseDto(responseMessage);
        return new Result(gptResponseDto);
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

    @PostMapping("/refresh")
    public ResponseModel<String> refreshConversation() {
        clearConversation();
//        conversation.removeIf(message -> !"system".equals(message.getRole()));
        return ResponseModel.success("Conversation refreshed successfully.");
    }

    private boolean isSaveChat(String content) {
        return content.contains(SAVE_MESSAGE);
    }

    private void clearConversation() {
        conversation.clear();
    }

    //    private boolean isEndChat(String responseMessage) {
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode responseJson = objectMapper.readTree(responseMessage);
//            return responseJson.has("foodName") && responseJson.has("ingredient") && responseJson.has("context");
//        } catch (IOException e) {
//            log.error("Failed to parse response message as JSON", e);
//            return false;
//        }
//    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}