package louise.controller;

import louise.controller.models.CheckResponse;
import louise.controller.models.QuizResponse;
import louise.domain.entities.Quiz;
import louise.repository.Document;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseConverter {

    public QuizResponse convert(Quiz quiz) {
        return new QuizResponse(quiz.id(), quiz.question(), quiz.answer());
    }

    public QuizResponse convert(Document document) {
        return new QuizResponse(document.getId(), document.getQuestion(), document.getAnswer());
    }

    public List<QuizResponse> convert(List<Quiz> quizList) {
        return quizList.stream().map(this::convert).toList();
    }

    public CheckResponse convert(String evaluation) {
        return new CheckResponse(evaluation);
    }
}
