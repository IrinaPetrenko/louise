package louise.domain.entities;

import lombok.*;

@Value
//@NoArgsConstructor
public class Quiz {
    long id;
    @NonNull
    String question;

    String answer;

    String userAnswer;

}
