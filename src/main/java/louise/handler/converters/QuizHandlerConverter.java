package louise.handler.converters;

import louise.controller.models.CheckRequest;
import louise.controller.models.QuestionRequest;
import louise.handler.entity.QuizHandlerObject;

public class QuizHandlerConverter {

    public static QuizHandlerObject convert(QuestionRequest questionRequest) {
        return new QuizHandlerObject(questionRequest.getQuestion(), null, null);
    }

    public static QuizHandlerObject convert(CheckRequest checkRequest) {
        return new QuizHandlerObject(null, checkRequest.getQuestionId(), checkRequest.getUserAnswer());
    }
}
