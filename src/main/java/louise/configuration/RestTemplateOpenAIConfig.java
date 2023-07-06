package louise.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateOpenAIConfig {

    @Autowired
    private ChatGptProps chatGptProps;

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
