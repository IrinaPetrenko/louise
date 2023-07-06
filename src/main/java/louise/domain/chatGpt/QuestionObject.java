package louise.domain.chatGpt;

import lombok.Data;

import java.util.List;

//@Accessors(fluent = true)
@Data
public class QuestionObject {
    private String model;
    private List<Message> messages;
    private Double temperature;
}
