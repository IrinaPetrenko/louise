package louise.exceptions;

import org.springframework.web.client.RestClientResponseException;

public class ChatGptConnectionException extends RuntimeException {

    public ChatGptConnectionException(RestClientResponseException exception) {
        super(exception);
    }
}
