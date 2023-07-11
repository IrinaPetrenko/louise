package louise.handler;

import louise.controller.models.CheckRequest;
import louise.controller.models.QuestionRequest;
import louise.domain.entities.Quiz;
import louise.handler.converters.QuizHandlerConverter;

import java.util.List;

public class QuizHandlerAdapter implements QuizInterface<QuestionRequest, CheckRequest> {


    protected QuizInterface quizHandler;

    public QuizHandlerAdapter(QuizInterface quizHandler) {
        this.quizHandler = quizHandler;
    }

    @Override
    public Quiz getRandom() {
        return quizHandler.getRandom();
    }

    @Override
    public Quiz create(QuestionRequest questionRequest) {
        return quizHandler.create(QuizHandlerConverter.convert(questionRequest));
    }

    @Override
    public void delete(long id) {
        quizHandler.delete(id);
    }

    @Override
    public List<Quiz> getAll() {
        return quizHandler.getAll();
    }

    @Override
    public String checkAnswer(CheckRequest userAnswer) {
        return quizHandler.checkAnswer(QuizHandlerConverter.convert(userAnswer));
    }
}
