package louise.domain.chatGpt;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;
import louise.configuration.ChatGptProps;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class Message {

    String role;
    String content;

    public Message(String content) {
        this.role = ChatGptProps.getInstance().getMessageRole();
        this.content = content;
    }
}
