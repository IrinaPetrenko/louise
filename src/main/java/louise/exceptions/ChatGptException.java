package louise.exceptions;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClientResponseException;

public class ChatGptException extends RuntimeException {

    private HttpStatusCode statusCode;

    public ChatGptException(RestClientResponseException exception) {
        super(String.format("Chat Gpt answered with exception - %s",exception.getMessage()));
        statusCode = exception.getStatusCode();
    }

    public HttpStatusCode getStatusCode() {
        return statusCode;
    }
}
