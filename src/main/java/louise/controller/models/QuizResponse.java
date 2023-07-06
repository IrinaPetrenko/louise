package louise.controller.models;

import lombok.Data;

@Data
public class QuizResponse {
    private long id;
    private String question;
    private String answer;
}
