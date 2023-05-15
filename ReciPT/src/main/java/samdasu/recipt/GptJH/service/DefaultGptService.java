package samdasu.recipt.GptJH.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import samdasu.recipt.GptJH.dto.chat.ChatMessage;
import samdasu.recipt.GptJH.dto.chat.ChatRequest;
import samdasu.recipt.GptJH.dto.chat.ChatResponse;
import samdasu.recipt.GptJH.exception.GptException;
import samdasu.recipt.GptJH.property.GptProperties;

import java.util.List;

@Slf4j
@Service
public class DefaultGptService implements GptService {

    protected final GptProperties gptProperties;

    private final String AUTHORIZATION;

    private final RestTemplate restTemplate = new RestTemplate();

    public DefaultGptService(GptProperties gptProperties) {
        this.gptProperties = gptProperties;
        AUTHORIZATION = "Bearer " + gptProperties.getApiKey();
    }

    @Override
    public String Chat(List<ChatMessage> messages) {
        ChatRequest multiChatRequest = new ChatRequest(gptProperties.getModel(), messages, gptProperties.getMaxTokens(), gptProperties.getTemperature(), gptProperties.getTopP());
        ChatResponse chatResponse = this.getResponse(this.buildHttpEntity(multiChatRequest), ChatResponse.class, gptProperties.getUrl());
        try {
            return chatResponse.getChoices().get(0).getMessage().getContent();
        } catch (Exception e) {
            log.error("parse chatgpt message error", e);
            throw e;
        }
    }

    @Override
    public ChatResponse ChatRequest(ChatRequest chatRequest) {
        return this.getResponse(this.buildHttpEntity(chatRequest), ChatResponse.class, gptProperties.getUrl());
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
            throw new GptException("error response status :" + responseEntity.getStatusCodeValue());
        } else {
            log.info("response: {}", responseEntity);
        }
        return responseEntity.getBody();
    }

}
