package louise.controller.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class QuestionRequest {
    @NotBlank(message = "This field should not be blank")
    @Size(min = 5, max = 150)
    private String question;
}
