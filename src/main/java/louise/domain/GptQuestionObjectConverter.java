package louise.domain;

import louise.configuration.ChatGptProps;
import louise.controller.models.QuestionRequest;
import louise.domain.chatGpt.Message;
import louise.domain.chatGpt.QuestionObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class GptQuestionObjectConverter {
    @Autowired
    private ChatGptProps props;
    public QuestionObject convert(QuestionRequest questionRequest) {
        return new QuestionObject(
                props.getModel(),
                new ArrayList<>(Arrays.asList(new Message(questionRequest.question()))),
                props.getTemperature()
        );
    }

    public QuestionObject convert(String question) {
        return new QuestionObject(
                props.getModel(),
                new ArrayList<>(Arrays.asList(new Message(question))),
                props.getTemperature()
        );
    }

}
