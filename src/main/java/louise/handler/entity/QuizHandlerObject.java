package louise.handler.entity;

import jakarta.annotation.Nullable;
import lombok.Data;

@Data
public class QuizHandlerObject {

    @Nullable
    private String question;
    @Nullable
    private long questionId;
    @Nullable
    private String userAnswer;
}
