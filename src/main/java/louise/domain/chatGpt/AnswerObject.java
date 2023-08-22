package louise.domain.chatGpt;

import java.util.List;

public record AnswerObject(String id,
                           String object,
                           Long created,
                           String model,
                           List<Choice> choices
) {

}
