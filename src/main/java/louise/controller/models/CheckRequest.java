package louise.controller.models;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class CheckRequest {
    @Min(4)
    private long questionId;
    private String userAnswer;

}
