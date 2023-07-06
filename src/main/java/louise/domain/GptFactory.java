package louise.domain;

import lombok.extern.slf4j.Slf4j;
import louise.configuration.ChatGptProps;
import louise.domain.chatGpt.AnswerObject;
import louise.domain.chatGpt.QuestionObject;
import louise.handler.entity.QuizHandlerObject;
import louise.repository.Document;
import louise.repository.RepositoryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
public class GptFactory {

    @Autowired
    private RepositoryFactory repositoryFactory;
    @Autowired
    private GptQuestionObjectConverter gptQuestionObjectConverter;
    @Autowired
    private ChatGptProps connectionProps;

    @Qualifier("openaiRestTemplate")
    @Autowired
    private RestTemplate restTemplate;

    public String request(QuizHandlerObject request) {
        QuestionObject gptRequest;
        if (request.getUserAnswer() != null) {
            gptRequest = prepareRequest(request.getQuestionId(), request.getUserAnswer());
        } else {
            gptRequest = gptQuestionObjectConverter.convert(request.getQuestion(), connectionProps);
        }
        return execute(gptRequest);
    }

    private String execute(QuestionObject request) {
        AnswerObject answer = restTemplate.postForObject(connectionProps.getUrl(), request, AnswerObject.class);
        Optional.ofNullable(answer.getChoices()).orElseThrow(NoSuchElementException::new);
        return answer.getChoices().get(0).getMessage().getContent();
    }


    private QuestionObject prepareRequest(long quizId, String userAnswer) {
        Document quiz = repositoryFactory.checkAndGetQuiz(quizId);
        String message = prepMessage(quiz.getQuestion(), quiz.getAnswer(), userAnswer);
        return gptQuestionObjectConverter.convert(message, connectionProps);
    }

    private String prepMessage(String question, String answer, String userAnswer) {
        return String.format("Hi, GPT. Given a question: %s. " +
                        "correct answer: %s. " +
                        "and user answer: %s. " +
                        "evaluate user answer correctness from 0 to 10 in comparison with correct answer.\n" +
                        "Give me evaluation number and explanation.",
                question, answer, userAnswer);
    }
}
