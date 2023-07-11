package louise.handler.entity;

import jakarta.annotation.Nullable;
import lombok.Value;

@Value
public class QuizHandlerObject {

    @Nullable
    String question;
    @Nullable
    Long questionId;
    @Nullable
    String userAnswer;
}
