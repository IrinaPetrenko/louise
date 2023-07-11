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

import java.util.List;

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
        log.info("Resetting mock for Gpt...");
        Mockito.reset(mockGptProps);
    }

    protected QuestionRequest buildQuestionRequest(String question) {
        return new QuestionRequest(question);
    }

    protected QuestionObject buildQuestionObject(String content) {
        return new QuestionObject(
                mockGptProps.getModel(),
                List.of(new Message(content, mockGptProps)),
                mockGptProps.getTemperature()
        );
    }

    protected AnswerObject buildAnswerObject(String answer) {
        return new AnswerObject(
                "234332",
                "object",
                (long) 23090293,
                mockGptProps.getModel(),
                List.of(buildChoice(answer))
        );
    }

    protected Choice buildChoice(String answer) {
        return new Choice(
                new Message(answer, mockGptProps),
                "test",
                7
        );
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
