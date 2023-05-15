package samdasu.recipt.GptJH.autoconfig;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import samdasu.recipt.GptJH.property.GptProperties;
import samdasu.recipt.GptJH.service.DefaultGptService;
import samdasu.recipt.GptJH.service.GptService;

@Slf4j
@Configuration
@EnableConfigurationProperties(GptProperties.class)
public class GptAutoConfiguration {

    private final GptProperties gptProperties;

    @Autowired
    public GptAutoConfiguration(GptProperties gptProperties) {
        this.gptProperties = gptProperties;
        log.debug("chatgpt-springboot-starter loaded.");
    }

    @Bean
    @ConditionalOnMissingBean(GptService.class)
    public GptService gptService() {
        return new DefaultGptService(gptProperties);
    }
}

