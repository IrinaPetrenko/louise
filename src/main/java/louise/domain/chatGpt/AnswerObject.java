package louise.domain.chatGpt;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class AnswerObject {

    String id;

    String object;

    Long created;

    String model;

    List<Choice> choices;

}
