package louise.domain.entities;

import lombok.NonNull;

public record Quiz(long id,
                   @NonNull
                   String question,
                   String answer,
                   String userAnswer
) {
}
