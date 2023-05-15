package samdasu.recipt.GptJH.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "gpt")
public class GptProperties {

    @Value("${gpt.api.key}")
    private String apiKey;
    private String url = "https://api.openai.com/v1/chat/completions";

    private String model = "gpt-3.5-turbo";

    private Integer maxTokens = 2000;

    private Double temperature = 1.0;

    private Double topP = 1.0;
}
