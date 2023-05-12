package samdasu.recipt.chatgpt.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import samdasu.recipt.chatgpt.controller.dto.ResponseModel;
import samdasu.recipt.chatgpt.dto.chat.MultiChatMessage;
import samdasu.recipt.chatgpt.service.ChatgptService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@Slf4j
@CrossOrigin
@RestController
public class GptApiController {

    @Autowired
    private ChatgptService chatgptService;

    @GetMapping("/send")
    public ResponseModel<String> send(HttpServletRequest request, @RequestParam String message) {
        String requestId = UUID.randomUUID().toString();
        log.info("requestId {}, ip {}, send a message : {}", requestId, request.getRemoteHost(), message);
        if (!StringUtils.hasText(message)) {
            return ResponseModel.fail("message can not be blank");
        }
        String responseMessage = chatgptService.sendMessage(message);
        log.info("requestId {}, ip {}\n get a reply : {}", requestId, request.getRemoteHost(), responseMessage);
        return ResponseModel.success(responseMessage);
    }

    @PostMapping("/multi/send")
    public ResponseModel<String> multiSend(HttpServletRequest request, @RequestBody List<MultiChatMessage> messages) {
        String requestId = UUID.randomUUID().toString();
        log.info("requestId {}, ip {}, send messages : {}", requestId, request.getRemoteHost(), messages.toString());
        if (CollectionUtils.isEmpty(messages)) {
            return ResponseModel.fail("messages can not be empty");
        }
        String responseMessage = chatgptService.multiChat(messages);
        log.info("requestId {}, ip {}\n get a reply :\n {}", requestId, request.getRemoteHost(), responseMessage);
        return ResponseModel.success(responseMessage);
    }

}