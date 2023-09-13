package samdasu.recipt.api.gpt.autoconfig;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import samdasu.recipt.api.gpt.property.ChatGptProperties;
import samdasu.recipt.api.gpt.service.ChatGptService;
import samdasu.recipt.api.gpt.service.ChatGptServiceImpl;

@Slf4j
@Configuration
@EnableConfigurationProperties(ChatGptProperties.class)
public class ChatGptAutoConfiguration {

    private final ChatGptProperties chatGptProperties;

    @Autowired
    public ChatGptAutoConfiguration(ChatGptProperties chatGptProperties) {
        this.chatGptProperties = chatGptProperties;
        log.debug("chatGpt-springboot-starter loaded.");
    }

    @Bean
    @ConditionalOnMissingBean(ChatGptService.class)
    public ChatGptService chatgptService() {
        return new ChatGptServiceImpl(chatGptProperties);
    }
}

