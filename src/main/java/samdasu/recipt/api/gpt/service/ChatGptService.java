package samdasu.recipt.api.gpt.service;

import org.springframework.scheduling.annotation.Async;
import samdasu.recipt.api.gpt.controller.dto.chat.Message;
import samdasu.recipt.api.gpt.controller.dto.chat.Request;
import samdasu.recipt.api.gpt.controller.dto.chat.Response;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ChatGptService {

    String getResponse(List<Message> conversation);

    @Async
    CompletableFuture<String> getResponseAsync(List<Message> conversation);

    Response sendRequest(Request multiChatRequest);
}
