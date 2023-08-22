package louise.handler;

import lombok.extern.slf4j.Slf4j;
import louise.MongoTestSetup;
import louise.domain.GptHandler;
import louise.domain.entities.Quiz;
import louise.exceptions.QuestionException;
import louise.handler.entity.QuizHandlerObject;
import louise.repository.QuizRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Random;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.reset;

@SpringBootTest
@Slf4j
@ExtendWith(MockitoExtension.class)
public class JavaQuizHandlerTest extends MongoTestSetup {

    @Autowired
    private JavaQuizHandler javaQuizHandler;

    @Autowired
    private QuizRepository quizRepository;

    @MockBean
    private GptHandler gptHandler;

    @Test
    public void testCreateQuiz() {
        String expectedQuestion = "test Q";
        String expectedAnswer = "test A";

        QuizHandlerObject quiz = new QuizHandlerObject(expectedQuestion, null, null);

        doAnswer(invocationOnMock -> expectedAnswer).when(gptHandler).request(quiz);

        Quiz actualQuiz = javaQuizHandler.create(quiz);
        Assertions.assertEquals(expectedQuestion, actualQuiz.question());
        Assertions.assertEquals(expectedAnswer, actualQuiz.answer());
    }

    @Test
    public void testCreatingDuplicateQuestion() {
        Quiz createdQuiz = createQuiz();

        QuizHandlerObject quiz = new QuizHandlerObject(createdQuiz.question(), null, null);

        Assertions.assertThrows(QuestionException.class, () -> {
            javaQuizHandler.create(quiz);
        });
    }

    @Test
    public void testDeleteQuiz() {
        Quiz createdQuiz = createQuiz();
        javaQuizHandler.delete(createdQuiz.id());
        Assertions.assertEquals(0, javaQuizHandler.getAll().size());
    }

    @Test
    public void testDeletingNotExistingQuiz() {
        Assertions.assertThrows(QuestionException.class, () -> {
            javaQuizHandler.delete(123445);
        });
    }

    @Test
    public void testGetAll() {
        Quiz createdQuiz1 = createQuiz();
        Quiz createdQuiz2 = createQuiz();

        List<Quiz> actualQuiz = javaQuizHandler.getAll();
        Assertions.assertEquals(2, actualQuiz.size());

        List<String> actualQuestions = actualQuiz.stream().map(Quiz::question).toList();
        Assertions.assertTrue(actualQuestions.contains(createdQuiz1.question()));
        Assertions.assertTrue(actualQuestions.contains(createdQuiz2.question()));
    }

    @Test
    public void testCorrectAnswer() {
        Quiz createdQuiz = createQuiz();

        String userAnswer = "This is mock User answer";
        String gptAnswer = "This is Mock. Test User answer is correct";
        QuizHandlerObject checkQuiz = new QuizHandlerObject(createdQuiz.question(), createdQuiz.id(), userAnswer);

        doAnswer(invocationOnMock -> gptAnswer).when(gptHandler).request(checkQuiz);
        Assertions.assertEquals(gptAnswer, javaQuizHandler.checkAnswer(checkQuiz));
    }

    private Quiz createQuiz() {
        Random random = new Random();
        String expectedQuestion = "test Q" + random.nextInt();
        String expectedAnswer = "test A" + random.nextInt();

        QuizHandlerObject quiz = new QuizHandlerObject(expectedQuestion, null, null);

        doAnswer(invocationOnMock -> expectedAnswer).when(gptHandler).request(quiz);

        return javaQuizHandler.create(quiz);
    }

    @AfterEach
    public void wrap() {
        quizRepository.deleteAll();
        reset(gptHandler);
    }
}
