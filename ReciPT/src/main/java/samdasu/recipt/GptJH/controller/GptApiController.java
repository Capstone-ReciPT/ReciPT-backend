package samdasu.recipt.GptJH.controller;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import samdasu.recipt.GptJH.controller.dto.ResponseModel;
import samdasu.recipt.GptJH.dto.chat.ChatMessage;
import samdasu.recipt.GptJH.service.GptService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/gpt")
public class GptApiController {

    @Autowired
    private GptService gptService;

    private Map<Integer, List<ChatMessage>> stockConversation = new HashMap<>();
    private Integer count = 0;
    private String temp;

    @PostMapping("/end")
    public void end() {
        log.info("저장된 대화 내용 삭제 완료!!");
        stockConversation.clear();
        count = 0;
    }

    @PostMapping("/send")
    public ResponseModel<String> multiSend(HttpServletRequest request, @RequestBody List<ChatMessage> messages) {
        temp = "";
        String requestId = sendMessage(request, messages);
        if (CollectionUtils.isEmpty(messages)) {
            return ResponseModel.fail("messages can not be empty");
        }
        String responseMessage = responseMessage(request, messages, requestId);

        return ResponseModel.success(responseMessage);
    }

    private String sendMessage(HttpServletRequest request, List<ChatMessage> messages) {
        String gptMessage = messages.toString();
        if (!MapUtils.isEmpty(stockConversation)) {
//            for (int i = 0; i < count; i++) {
//                temp += stockConversation.get(i).toString();
//            }
            for (Integer key : stockConversation.keySet()) {
                String value = stockConversation.get(key).toString();
                temp += value;
            }
            gptMessage = temp + gptMessage;
        }
        stockConversation.put(count++, messages); //사용자의 요청 값 저장
        String cleanedMessage = gptMessage.replaceAll("\\[|\\]", "");
//        String sub = "";
//        int index = cleanedMessage.indexOf("assistant");
//        while (index != -1) {
//            sub = cleanedMessage.substring(index - 17) + ", 붸";
//            index = cleanedMessage.indexOf("assistant", index + 9);
//        }

        String message = "[" + cleanedMessage + "]";

        String requestId = UUID.randomUUID().toString();
        log.info("requestId {}, ip {}, send messages : {}", requestId, request.getRemoteHost(), message);
        return requestId;
    }

    private String responseMessage(HttpServletRequest request, List<ChatMessage> messages, String requestId) {
        ChatMessage response = gptService.Chat(messages);

        stockConversation.put(count++, Collections.singletonList(response)); //gpt 응답 값 저장
        String responseMessage = response.getContent().toString();
        log.info("requestId {}, ip {}\n get a reply : {}", requestId, request.getRemoteHost(), responseMessage);
        return responseMessage;
    }
}