package louise.domain.chatGpt;

import lombok.Data;
import lombok.NoArgsConstructor;
import louise.configuration.ChatGptProps;

@Data
@NoArgsConstructor
public class Message {

    private String role;
    private String content;

    public Message(String content, ChatGptProps props) {
        this.role = props.getMessageRole();
        this.content = content;
    }
}
