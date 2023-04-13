package com.unfbx.chatgptsteamoutput.listener;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.unfbx.chatgpt.entity.chat.ChatCompletionResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Objects;

@Slf4j
public class OpenAISSEEventSourceListener extends EventSourceListener {

    private SseEmitter sseEmitter;

    public OpenAISSEEventSourceListener(SseEmitter sseEmitter) {
        this.sseEmitter = sseEmitter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onOpen(EventSource eventSource, Response response) {
        log.info("OpenAI sse 연결 설정...");
    }

    /**
     * {@inheritDoc}
     */
    @SneakyThrows
    @Override
    public void onEvent(EventSource eventSource, String id, String type, String data) {
        log.info("OpenAI 데이터 반환：{}", data);
//        parsingResult(data);
        if (data.equals("[DONE]")) {
            log.info("OpenAI 데이터 반환 완료");
            sseEmitter.send(SseEmitter.event()
                    .id("[DONE]")
                    .data("[DONE]")
                    .reconnectTime(3000));
            return;
        }
        ObjectMapper mapper = new ObjectMapper();
        ChatCompletionResponse completionResponse = mapper.readValue(data, ChatCompletionResponse.class); // Json 읽기
        sseEmitter.send(SseEmitter.event()
                .id(completionResponse.getId())
                .data(completionResponse.getChoices().get(0).getDelta())
                .reconnectTime(3000));
    }

//    private void parsingResult(String result) {
////        {"id":"chatcmpl-73Escn9ukCmwUaOijR3aRaNMQeeoB","object":"chat.completion.chunk","created":1681006538,"model":"gpt-3.5-turbo-0301","choices":[{"delta":{"content":" 도"},"index":0,"finish_reason":null}]}
//        try {
//            JSONParser jsonParser = new JSONParser();
//            JSONObject jsonObj = (JSONObject) jsonParser.parse(result);
//            JSONArray GptRespone = (JSONArray) jsonObj.get("id");
//
//            System.out.println("=====Gpt=====");
//
//            for (int i = 0; i < GptRespone.size(); i++) {
//                JSONObject tempObj = (JSONObject) GptRespone.get(i);
//                System.out.println("" + (i + 1) + "번째 delta : " + tempObj.get("delta"));
//                System.out.println("----------------------------");
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void onClosed(EventSource eventSource) {
        log.info("OpenAI Sse 연결 닫기...");
    }


    @SneakyThrows
    @Override
    public void onFailure(EventSource eventSource, Throwable t, Response response) {
        if (Objects.isNull(response)) {
            return;
        }
        ResponseBody body = response.body();
        if (Objects.nonNull(body)) {
            log.error("OpenAI sse 연결 이상 data:{}, 이상:{}", body.string(), t);
        } else {
            log.error("OpenAI sse 연결 이상 data:{}, 이상:{}", response, t);
        }
        eventSource.cancel();
    }
}
