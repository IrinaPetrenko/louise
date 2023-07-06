package louise.handler.converters;

import louise.controller.models.CheckRequest;
import louise.controller.models.QuestionRequest;
import louise.handler.entity.QuizHandlerObject;

public class QuizHandlerConverter {

    public static QuizHandlerObject convert(QuestionRequest questionRequest) {
        QuizHandlerObject quizHandler = new QuizHandlerObject();
        quizHandler.setQuestion(questionRequest.getQuestion());
        return quizHandler;
    }

    public static QuizHandlerObject convert(CheckRequest checkRequest) {
        QuizHandlerObject quizHandler = new QuizHandlerObject();
        quizHandler.setQuestionId(checkRequest.getQuestionId());
        quizHandler.setUserAnswer(checkRequest.getUserAnswer());
        return quizHandler;
    }
}
