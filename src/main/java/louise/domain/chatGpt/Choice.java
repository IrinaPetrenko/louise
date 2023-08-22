package louise.domain.chatGpt;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Choice(Message message,
                     @JsonProperty("finish_reason")
                     String finishReason,
                     Integer index
) {
}
