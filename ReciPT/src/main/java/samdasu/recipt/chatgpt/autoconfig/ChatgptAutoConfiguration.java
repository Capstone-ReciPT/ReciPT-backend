package samdasu.recipt.chatgpt.autoconfig;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import samdasu.recipt.chatgpt.property.ChatgptProperties;
import samdasu.recipt.chatgpt.service.ChatgptService;
import samdasu.recipt.chatgpt.service.DefaultChatgptService;

@Slf4j
@Configuration
@EnableConfigurationProperties(ChatgptProperties.class)
public class ChatgptAutoConfiguration {

    private final ChatgptProperties chatgptProperties;

    @Autowired
    public ChatgptAutoConfiguration(ChatgptProperties chatgptProperties){
        this.chatgptProperties = chatgptProperties;
        log.debug("chatgpt-springboot-starter loaded.");
    }

    @Bean
    @ConditionalOnMissingBean(ChatgptService.class)
    public ChatgptService chatgptService(){
        return new DefaultChatgptService(chatgptProperties);
    }
}

