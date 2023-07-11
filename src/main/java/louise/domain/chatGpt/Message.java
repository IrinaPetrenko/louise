package louise.domain.chatGpt;

import lombok.*;
import louise.configuration.ChatGptProps;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class Message {

    String role;
    String content;

    public Message(String content, ChatGptProps props) {
        this.role = props.getMessageRole();
        this.content = content;
    }
}
