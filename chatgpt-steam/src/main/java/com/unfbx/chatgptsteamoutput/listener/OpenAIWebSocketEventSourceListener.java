package com.unfbx.chatgptsteamoutput.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unfbx.chatgpt.entity.chat.ChatCompletionResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;

import javax.websocket.Session;
import java.util.Objects;

/**
 * 描述：OpenAI流式输出Socket接收
 *
 * @author https:www.unfbx.com
 * @date 2023-03-23
 */
@Slf4j
public class OpenAIWebSocketEventSourceListener extends EventSourceListener {

    private Session session;

    public OpenAIWebSocketEventSourceListener(Session session) {
        this.session = session;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onOpen(EventSource eventSource, Response response) {
        log.info("OpenAI Websocket 연결 설정...");
    }

    /**
     * {@inheritDoc}
     */
    @SneakyThrows
    @Override
    public void onEvent(EventSource eventSource, String id, String type, String data) {
        log.info("OpenAI 데이터를 반환：{}", data);
        if (data.equals("[DONE]")) {
            log.info("OpenAI가 데이터를 반환했습니다.");
            session.getBasicRemote().sendText("[DONE]");
            return;
        }
        ObjectMapper mapper = new ObjectMapper();
        ChatCompletionResponse completionResponse = mapper.readValue(data, ChatCompletionResponse.class); // Json 읽기
        String delta = mapper.writeValueAsString(completionResponse.getChoices().get(0).getDelta());
        session.getBasicRemote().sendText(delta);
    }


    @Override
    public void onClosed(EventSource eventSource) {
        log.info("OpenAI Websocket 연결 닫기...");
    }


    @SneakyThrows
    @Override
    public void onFailure(EventSource eventSource, Throwable t, Response response) {
        if (Objects.isNull(response)) {
            return;
        }
        ResponseBody body = response.body();
        if (Objects.nonNull(body)) {
            log.error("OpenAI Websocket 연결 이상 data:{}, 이상:{}", body.string(), t);
        } else {
            log.error("OpenAI Websocket 연결 이상 data:{}, 이상:{}", response, t);
        }
        eventSource.cancel();
    }
}
