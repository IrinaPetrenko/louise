package louise.domain.chatGpt;

import lombok.Value;

import java.util.List;

@Value
public class QuestionObject {
    String model;
    List<Message> messages;
    Double temperature;
}
