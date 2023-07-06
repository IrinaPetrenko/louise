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
        QuizResponse quizResponse = new QuizResponse();
        quizResponse.setId(quiz.getId());
        quizResponse.setAnswer(quiz.getAnswer());
        quizResponse.setQuestion(quiz.getQuestion());
        return quizResponse;
    }

    public QuizResponse convert(Document document) {
        QuizResponse quizResponse = new QuizResponse();
        quizResponse.setId(document.getId());
        quizResponse.setQuestion(document.getQuestion());
        quizResponse.setAnswer(document.getAnswer());
        return quizResponse;
    }

    public List<QuizResponse> convert(List<Quiz> quizList) {
        return quizList.stream().map(this::convert).toList();
    }

    public CheckResponse convert(String evaluation) {
        CheckResponse response = new CheckResponse();
        response.setResponse(evaluation);
        return response;
    }
}
