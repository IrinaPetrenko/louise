package louise.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class RestTemplateOpenAIConfig {

    private final ChatGptProps chatGptProps;

    @Bean
    @Qualifier("openaiRestTemplate")
    public RestTemplate OpenAIRestTemplateConfig() {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getInterceptors().add((request, body, execution) -> {
                    request.getHeaders().add("Authorization", chatGptProps.getKey());
                    return execution.execute(request, body);
                }
        );
        return restTemplate;
    }
}
