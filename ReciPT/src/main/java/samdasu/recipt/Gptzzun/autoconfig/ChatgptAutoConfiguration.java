package samdasu.recipt.Gptzzun.autoconfig;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import samdasu.recipt.Gptzzun.property.MultiChatProperties;
import samdasu.recipt.Gptzzun.service.ChatgptService;
import samdasu.recipt.Gptzzun.service.DefaultChatgptService;

@Slf4j
@Configuration
@EnableConfigurationProperties(MultiChatProperties.class)
public class ChatgptAutoConfiguration {

    private final MultiChatProperties multiChatProperties;

    @Autowired
    public ChatgptAutoConfiguration(MultiChatProperties multiChatProperties) {
        this.multiChatProperties = multiChatProperties;
        log.debug("chatgpt-springboot-starter loaded.");
    }

    @Bean
    @ConditionalOnMissingBean(ChatgptService.class)
    public ChatgptService chatgptService() {
        return new DefaultChatgptService(multiChatProperties);
    }
}

