package louise.controller.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record QuestionRequest(
        @JsonProperty("question")
        @NotBlank(message = "This field should not be blank")
        @Size(min = 5, max = 150)
        String question) {
}
