package louise.controller.models;

import jakarta.validation.constraints.Min;
import lombok.Value;

@Value
public class CheckRequest {
    @Min(4)
    long questionId;
    String userAnswer;

}
