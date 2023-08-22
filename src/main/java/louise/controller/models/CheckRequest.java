package louise.controller.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;

public record CheckRequest(
                           @JsonProperty("questionId")
                           @Min(4)
                           long questionId,
                           @JsonProperty("userAnswer")
                           String userAnswer
) {
}
