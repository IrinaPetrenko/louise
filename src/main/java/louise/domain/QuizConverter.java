package louise.domain;

import louise.domain.entities.Quiz;
import louise.repository.Document;
import org.springframework.stereotype.Service;

@Service
public class QuizConverter {
    public Quiz convert(Document document) {
        return new Quiz(document.getId(), document.getQuestion(), document.getAnswer(), null);
    }
}
