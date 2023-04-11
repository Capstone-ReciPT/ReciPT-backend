package com.unfbx.chatgptsteamoutput.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;


@Configuration
public class WebSocketConfig {
    /**
     * 이 bean의 등록은, @ServerEndpoint가 있는 주석을 websocket으로 스캔하는데 사용되며, 만약 당신이 외장된 tomcat을 사용한다면 이 프로필은 필요하지 않습니다.
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
