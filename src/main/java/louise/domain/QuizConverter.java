package louise.domain;

import louise.domain.entities.Quiz;
import louise.repository.Document;
import org.springframework.stereotype.Service;

@Service
public class QuizConverter {
    public Quiz convert(Document document) {
        Quiz quiz = new Quiz();
        quiz.setId(document.getId());
        quiz.setQuestion(document.getQuestion());
        quiz.setAnswer(document.getAnswer());
        return quiz;
    }
}
