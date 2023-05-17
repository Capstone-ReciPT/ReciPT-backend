package samdasu.recipt.GptJH.controller;


import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import samdasu.recipt.GptJH.controller.dto.RecipeDataJson;
import samdasu.recipt.GptJH.controller.dto.ResponseModel;
import samdasu.recipt.GptJH.dto.chat.ChatMessage;
import samdasu.recipt.GptJH.service.GptService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/gpt")
public class GptApiController {

    @Autowired
    private GptService gptService;

    private List<List<ChatMessage>> stock = new ArrayList<>();

    @PostMapping("/end")
    public ResponseModel<String> end(HttpServletRequest request) {
        log.info("대화 종료!!");
        ChatMessage message = new ChatMessage();
        message.setRole("user");
        message.setContent("종료");
        List<ChatMessage> messages = Arrays.asList(message);

        String requestId = sendMessage(request, messages);
        log.info("messages.stream().collect(Collectors.toList()) = {}", messages.stream().collect(Collectors.toList()));
        if (CollectionUtils.isEmpty(messages)) {
            return ResponseModel.fail("messages can not be empty");
        }
        String responseMessage = responseMessage(request, messages, requestId);
        return ResponseModel.success(responseMessage);
    }

    @PostMapping("/send/recommendfood")
    public ResponseModel<String> recommendFood(HttpServletRequest request, @RequestBody List<ChatMessage> messages) {
        String requestId = sendMessage(request, messages);
        if (CollectionUtils.isEmpty(messages)) {
            return ResponseModel.fail("messages can not be empty");
        }
        String responseMessage = responseMessage(request, messages, requestId);
        return ResponseModel.success(responseMessage);
    }

    private String sendMessage(HttpServletRequest request, List<ChatMessage> messages) {
//        String temp = "";
//        String gptMessage = messages.toString();
//
//        if (!stock.isEmpty()) {
//            for (List<ChatMessage> chatMessages : stock) {
//                String str = chatMessages.toString();
//                temp += str;
//            }
//            log.info("temp = {}", temp);
//            gptMessage = temp + gptMessage;
//        }
//        stock.add(messages);
//
//        String output = gptMessage.replaceAll("\\]\\[", ", ");
//
//        for (List<ChatMessage> chatMessages : stock) {
//            log.info("chatMessages.stream().toString() = {}", chatMessages.stream().collect(Collectors.toList()));
//        }

        String requestId = UUID.randomUUID().toString();
        log.info("requestId {}, ip {}, send messages : {}", requestId, request.getRemoteHost(), messages);
        return requestId;
    }

    private String responseMessage(HttpServletRequest request, List<ChatMessage> messages, String requestId) {
        ChatMessage response = gptService.Chat(messages);

//        stock.add(Collections.singletonList(response));

        log.info("requestId {}, ip {}\n get a reply : {}", requestId, request.getRemoteHost(), response);
        return response.getContent();
    }

    private String makeJson(ChatMessage response) {
        String responseMessage = response.getContent().toString();
        Gson gson = new Gson();
        RecipeDataJson recipeData = gson.fromJson(responseMessage, RecipeDataJson.class);

        recommendFood(recipeData);
        return responseMessage;
    }

    public Result recommendFood(RecipeDataJson recipeData) {
        log.info("recommendFood 메서드 호출 성공");
        return new Result(recipeData);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T recipe;
    }
}