package samdasu.recipt.Gptzzun.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "chatgpt")
public class MultiChatProperties {

    private String apiKey = "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";
    private String url = "https://api.openai.com/v1/chat/completions";

    private String model = "gpt-3.5-turbo";

    private Integer maxTokens = 2000;

    private Double temperature = 1.0;

    private Double topP = 1.0;
}
