package louise;

import lombok.extern.slf4j.Slf4j;
import louise.configuration.ChatGptProps;
import louise.controller.models.CheckRequest;
import louise.controller.models.QuestionRequest;
import louise.domain.chatGpt.AnswerObject;
import louise.domain.chatGpt.Choice;
import louise.domain.chatGpt.Message;
import louise.domain.chatGpt.QuestionObject;
import louise.handler.entity.QuizHandlerObject;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Arrays;

@Slf4j
public class TestSetup extends MongoTestSetup {
    @Mock
    public ChatGptProps mockGptProps;

    @Before
    public void prep() {
        log.info("Preparing mock for Gpt...");
        Mockito.when(mockGptProps.getModel()).thenReturn("test-gpt-model");
        Mockito.when(mockGptProps.getTemperature()).thenReturn(0.7);
        Mockito.when(mockGptProps.getMessageRole()).thenReturn("test");
        Mockito.when(mockGptProps.getUrl()).thenReturn("https://api.openai.com/v1/chat/completions");
        Mockito.when(mockGptProps.getKey()).thenReturn("test-key");
    }

    @AfterEach
    public void wrap() {
        log.info("Reseting mock for Gpt...");
        Mockito.reset(mockGptProps);
    }

    protected QuestionRequest buildQuestionRequest(String question) {
        return new QuestionRequest(question);
    }

    protected QuestionObject buildQuestionObject(String content) {
        QuestionObject question = new QuestionObject();
        question.setModel(mockGptProps.getModel());
        question.setMessages(Arrays.asList(new Message(content, mockGptProps)));
        question.setTemperature(mockGptProps.getTemperature());
        return question;
    }

    protected AnswerObject buildAnswerObject(String answer) {
        AnswerObject answerObject = new AnswerObject();
        answerObject.setObject("object");
        answerObject.setId("234332");
        answerObject.setModel(mockGptProps.getModel());
        answerObject.setCreated((long) 23090293);
        answerObject.setChoices(Arrays.asList(buildChoice(answer)));
        return answerObject;
    }

    protected Choice buildChoice(String answer) {
        Choice choice = new Choice();
        choice.setFinishReason("test");
        choice.setIndex(7);
        choice.setMessage(new Message(answer, mockGptProps));
        return choice;
    }

    protected QuizHandlerObject buildQuizHandler(String question) {
        return new QuizHandlerObject(question, null, null);
    }

    protected QuizHandlerObject buildQuizHandler(String question, long questionId, String userAnswer) {
        return new QuizHandlerObject(question, questionId, userAnswer);
    }

    protected CheckRequest buildCheckRequest(long questionId, String userAnswer) {
        return new CheckRequest(questionId, userAnswer);
    }
}
