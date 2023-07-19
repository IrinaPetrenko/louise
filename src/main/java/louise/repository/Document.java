package louise.repository;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
@org.springframework.data.mongodb.core.mapping.Document(collection = "quiz")
@NoArgsConstructor
@AllArgsConstructor
public class Document {
    @Id
    private long id;
    @Indexed(unique = true)
    @NotEmpty
    private String question;
    private String answer;
}
