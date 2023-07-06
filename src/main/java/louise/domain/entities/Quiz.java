package louise.domain.entities;

import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class Quiz {
    private long id;
    @NonNull
    private String question;

    private String answer;

    private String userAnswer;

}
