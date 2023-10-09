package samdasu.recipt.domain.config.grapana;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.binder.MeterBinder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import samdasu.recipt.api.gpt.controller.dto.chat.Message;
import samdasu.recipt.api.gpt.service.ChatGptService;

@Slf4j
@Configuration
public class GauageConfig {
//    @Bean
//    public MeterBinder gptResponseSize(ChatGptService chatGptService) {
//        return registry -> Gauge.builder("chatGpt.service", chatGptService, service -> {
//            log.info("chatGpt");
//            return service.getResponse(conversation).get();
//        }).register(registry);
//    }
}
