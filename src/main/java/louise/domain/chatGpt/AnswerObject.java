package louise.domain.chatGpt;

import lombok.Data;

import java.util.List;

@Data
public class AnswerObject {

    private String id;

    private String object;

    private Long created;

    private String model;

    private List<Choice> choices;

}
