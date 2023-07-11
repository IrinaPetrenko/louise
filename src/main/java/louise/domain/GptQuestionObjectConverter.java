package louise.domain;

import louise.configuration.ChatGptProps;
import louise.controller.models.QuestionRequest;
import louise.domain.chatGpt.Message;
import louise.domain.chatGpt.QuestionObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class GptQuestionObjectConverter {
    public QuestionObject convert(QuestionRequest questionRequest, ChatGptProps props) {
        return new QuestionObject(
                props.getModel(),
                new ArrayList<>(Arrays.asList(new Message(questionRequest.getQuestion(), props))),
                props.getTemperature()
        );
    }

    public QuestionObject convert(String question, ChatGptProps props) {
        return new QuestionObject(
                props.getModel(),
                new ArrayList<>(Arrays.asList(new Message(question, props))),
                props.getTemperature()
        );
    }

}
