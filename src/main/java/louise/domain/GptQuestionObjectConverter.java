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
        QuestionObject questionObj = new QuestionObject();
        questionObj.setMessages(new ArrayList<>(Arrays.asList(new Message(questionRequest.getQuestion(), props))));
        questionObj.setModel(props.getModel());
        questionObj.setTemperature(props.getTemperature());
        return questionObj;
    }

    public QuestionObject convert(String question, ChatGptProps props) {
        QuestionObject questionObj = new QuestionObject();
        questionObj.setMessages(new ArrayList<>(Arrays.asList(new Message(question, props))));
        questionObj.setModel(props.getModel());
        questionObj.setTemperature(props.getTemperature());
        return questionObj;
    }

}
