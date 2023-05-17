package samdasu.recipt.Gptzzun.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import samdasu.recipt.Gptzzun.dto.Usage;
import samdasu.recipt.Gptzzun.dto.chat.MultiChatMessage;
import samdasu.recipt.Gptzzun.dto.chat.MultiChatRequest;
import samdasu.recipt.Gptzzun.dto.chat.MultiChatResponse;
import samdasu.recipt.Gptzzun.dto.chat.MultiResponseChoice;
import samdasu.recipt.Gptzzun.exception.ChatgptException;
import samdasu.recipt.Gptzzun.property.MultiChatProperties;

import java.util.List;

@Slf4j
@Service
public class DefaultChatgptService implements ChatgptService {

    protected final MultiChatProperties multiChatProperties;

    private final String AUTHORIZATION;

    private final RestTemplate restTemplate = new RestTemplate();

    public DefaultChatgptService(MultiChatProperties multiChatProperties) {
        this.multiChatProperties = multiChatProperties;
        AUTHORIZATION = "Bearer " + multiChatProperties.getApiKey();
    }

    @Override
    public String multiChat(List<MultiChatMessage> conversation) {
        MultiChatRequest multiChatRequest = new MultiChatRequest(
                multiChatProperties.getModel(),
                conversation,
                multiChatProperties.getMaxTokens(),
                multiChatProperties.getTemperature(),
                multiChatProperties.getTopP()
        );
        MultiChatResponse multiChatResponse = this.getResponse(
                this.buildHttpEntity(multiChatRequest),
                MultiChatResponse.class,
                multiChatProperties.getUrl()
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

            MultiResponseChoice responseChoice = multiChatResponse.getChoices().get(0);
            MultiChatMessage responseMessage = responseChoice.getMessage();
            conversation.add(responseMessage);
            return responseMessage.getContent();
        } catch (Exception e) {
            log.error("parse chatgpt message error", e);
            throw e;
        }
    }

    @Override
    public MultiChatResponse multiChatRequest(MultiChatRequest multiChatRequest) {
        return this.getResponse(this.buildHttpEntity(multiChatRequest), MultiChatResponse.class, multiChatProperties.getUrl());
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
