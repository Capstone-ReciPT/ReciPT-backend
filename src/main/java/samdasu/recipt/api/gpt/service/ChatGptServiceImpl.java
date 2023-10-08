package samdasu.recipt.api.gpt.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import samdasu.recipt.api.gpt.controller.dto.Usage;
import samdasu.recipt.api.gpt.controller.dto.chat.Message;
import samdasu.recipt.api.gpt.controller.dto.chat.Request;
import samdasu.recipt.api.gpt.controller.dto.chat.Response;
import samdasu.recipt.api.gpt.controller.dto.chat.ResponseChoice;
import samdasu.recipt.api.gpt.exception.ChatGptException;
import samdasu.recipt.api.gpt.exception.MaxTokenException;
import samdasu.recipt.api.gpt.property.ChatGptProperties;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class ChatGptServiceImpl implements ChatGptService {

    protected final ChatGptProperties chatGptProperties;

    private final String AUTHORIZATION;

    private final RestTemplate restTemplate = new RestTemplate();

    public ChatGptServiceImpl(ChatGptProperties chatGptProperties) {
        this.chatGptProperties = chatGptProperties;
        AUTHORIZATION = "Bearer " + chatGptProperties.getApiKey();
    }

    @Override
    public String getResponse(List<Message> conversation) {
        Request multiChatRequest = new Request(
                chatGptProperties.getModel(),
                conversation,
                chatGptProperties.getMaxTokens(),
                chatGptProperties.getTemperature(),
                chatGptProperties.getTopP()
        );
        Response multiChatResponse = this.getResponse(
                this.buildHttpEntity(multiChatRequest),
                chatGptProperties.getUrl()
        );
        try {
            Usage usage = multiChatResponse.getUsage();
            if (usage != null) {
                Integer promptTokens = usage.getPromptTokens();
                Integer completionTokens = usage.getCompletionTokens();
                Integer totalTokens = usage.getTotalTokens();

                log.info("Prompt Tokens: {}", promptTokens);
                log.info("Completion Tokens: {}", completionTokens);
                log.info("Total Tokens: {}", totalTokens);
            }

            ResponseChoice responseChoice = multiChatResponse.getChoices().get(0);
            Message responseMessage = responseChoice.getMessage();
            conversation.add(responseMessage);
            return responseMessage.getContent();
        } catch (Exception e) {
            log.error("parse chatgpt message error", e);
            throw e;
        }
    }

    @Async
    @Override
    public CompletableFuture<String> getResponseAsync(List<Message> conversation) {
        return CompletableFuture.supplyAsync(() -> {
            Request multiChatRequest = new Request(
                    chatGptProperties.getModel(),
                    conversation,
                    chatGptProperties.getMaxTokens(),
                    chatGptProperties.getTemperature(),
                    chatGptProperties.getTopP()
            );
            Response multiChatResponse = this.getResponse(
                    this.buildHttpEntity(multiChatRequest),
                    chatGptProperties.getUrl()
            );

            try {
                Usage usage = multiChatResponse.getUsage();
                if (usage != null) {
                    Integer promptTokens = usage.getPromptTokens();
                    Integer completionTokens = usage.getCompletionTokens();
                    Integer totalTokens = usage.getTotalTokens();

                    log.info("Prompt Tokens: {}", promptTokens);
                    log.info("Completion Tokens: {}", completionTokens);
                    log.info("Total Tokens: {}", totalTokens);
                }

                ResponseChoice responseChoice = multiChatResponse.getChoices().get(0);
                Message responseMessage = responseChoice.getMessage();
                conversation.add(responseMessage);
                return responseMessage.getContent();
            } catch (Exception e) {
                log.error("parse chatgpt message error", e);
                throw e;
            }
        });
    }

    @Override
    public Response sendRequest(Request multiChatRequest) {
        return this.getResponse(this.buildHttpEntity(multiChatRequest), chatGptProperties.getUrl());
    }

    protected <T> HttpEntity<?> buildHttpEntity(T request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/json; charset=UTF-8"));
        headers.add("Authorization", AUTHORIZATION);
        return new HttpEntity<>(request, headers);
    }

    protected <T> T getResponse(HttpEntity<?> httpEntity, String url) {
        log.info("request url: {}, httpEntity: {}", url, httpEntity);
        if (Objects.requireNonNull(httpEntity.getBody()).toString().length() >= 16385) {
            throw new MaxTokenException("Over maximum context length!!");
        }
        ResponseEntity<T> responseEntity = restTemplate.postForEntity(url, httpEntity, (Class<T>) Response.class);
        if (responseEntity.getStatusCodeValue() != HttpStatus.OK.value()) {
            log.error("error response status: {}", responseEntity);
            throw new ChatGptException("error response status :" + responseEntity.getStatusCodeValue());
        } else {
            log.info("response: {}", responseEntity);
        }
        return responseEntity.getBody();
    }
}
