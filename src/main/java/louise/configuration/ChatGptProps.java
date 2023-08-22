package louise.configuration;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "openai.api")
@Data
public class ChatGptProps {

    private String key;

    private String url;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.temperature}")
    private Double temperature;

    @Value("${openai.message.role}")
    private String messageRole;

    private static ChatGptProps instance;

    public static ChatGptProps getInstance() {
        return instance;
    }
    @PostConstruct
    private void registerInstance() {
        instance = this;
    }
}
