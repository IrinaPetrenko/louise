package louise.controller.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record QuizResponse(@JsonProperty("id") long id,
                           @JsonProperty("question") String question,
                           @JsonProperty("answer") String answer) {
}
