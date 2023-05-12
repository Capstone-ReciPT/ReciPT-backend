package samdasu.recipt.chatgpt.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import samdasu.recipt.chatgpt.dto.ChatRequest;
import samdasu.recipt.chatgpt.dto.ChatResponse;
import samdasu.recipt.chatgpt.dto.chat.MultiChatMessage;
import samdasu.recipt.chatgpt.dto.chat.MultiChatRequest;
import samdasu.recipt.chatgpt.dto.chat.MultiChatResponse;
import samdasu.recipt.chatgpt.exception.ChatgptException;
import samdasu.recipt.chatgpt.property.ChatgptProperties;

import java.util.List;

@Slf4j
@Service
public class DefaultChatgptService implements ChatgptService {

    protected final ChatgptProperties chatgptProperties;

    private final String AUTHORIZATION;

    private final RestTemplate restTemplate = new RestTemplate();

    public DefaultChatgptService(ChatgptProperties chatgptProperties) {
        this.chatgptProperties = chatgptProperties;
        AUTHORIZATION = "Bearer " + chatgptProperties.getApiKey();
    }

    @Override
    public String sendMessage(String message) {
        ChatRequest chatRequest = new ChatRequest(chatgptProperties.getModel(), message, chatgptProperties.getMaxTokens(), chatgptProperties.getTemperature(), chatgptProperties.getTopP());
        ChatResponse chatResponse = this.getResponse(this.buildHttpEntity(chatRequest), ChatResponse.class, chatgptProperties.getUrl());
        try {
            return chatResponse.getChoices().get(0).getText();
        } catch (Exception e) {
            log.error("parse chatgpt message error", e);
            throw e;
        }
    }

    @Override
    public ChatResponse sendChatRequest(ChatRequest chatRequest) {
        return this.getResponse(this.buildHttpEntity(chatRequest), ChatResponse.class, chatgptProperties.getUrl());
    }

    @Override
    public String multiChat(List<MultiChatMessage> messages) {
        MultiChatRequest multiChatRequest = new MultiChatRequest(chatgptProperties.getMulti().getModel(), messages, chatgptProperties.getMulti().getMaxTokens(), chatgptProperties.getMulti().getTemperature(), chatgptProperties.getMulti().getTopP());
        MultiChatResponse multiChatResponse = this.getResponse(this.buildHttpEntity(multiChatRequest), MultiChatResponse.class, chatgptProperties.getMulti().getUrl());
        try {
            return multiChatResponse.getChoices().get(0).getMessage().getContent();
        } catch (Exception e) {
            log.error("parse chatgpt message error", e);
            throw e;
        }
    }

    @Override
    public MultiChatResponse multiChatRequest(MultiChatRequest multiChatRequest) {
        return this.getResponse(this.buildHttpEntity(multiChatRequest), MultiChatResponse.class, chatgptProperties.getMulti().getUrl());
    }

    protected <T> HttpEntity<?> buildHttpEntity(T request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/json; charset=UTF-8"));
        headers.add("Authorization", AUTHORIZATION);
        return new HttpEntity<>(request, headers);
    }

    protected <T> T getResponse(HttpEntity<?> httpEntity, Class<T> responseType, String url) {
        log.info("request url: {}, httpEntity: {}", url, httpEntity);
        ResponseEntity<T> responseEntity = restTemplate.postForEntity(url, httpEntity, responseType);
        if (responseEntity.getStatusCodeValue() != HttpStatus.OK.value()) {
            log.error("error response status: {}", responseEntity);
            throw new ChatgptException("error response status :" + responseEntity.getStatusCodeValue());
        } else {
            log.info("response: {}", responseEntity);
        }
        return responseEntity.getBody();
    }

}
