package louise.handler;

import lombok.extern.slf4j.Slf4j;
import louise.MongoTestSetup;
import louise.domain.GptFactory;
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
    private GptFactory gptFactory;

    @Test
    public void testCreateQuiz() {
        String expectedQuestion = "test Q";
        String expectedAnswer = "test A";

        QuizHandlerObject quiz = new QuizHandlerObject();
        quiz.setQuestion(expectedQuestion);

        doAnswer(invocationOnMock -> expectedAnswer).when(gptFactory).request(quiz);

        Quiz actualQuiz = javaQuizHandler.create(quiz);
        Assertions.assertEquals(expectedQuestion, actualQuiz.getQuestion());
        Assertions.assertEquals(expectedAnswer, actualQuiz.getAnswer());
    }

    @Test
    public void testCreatingDuplicateQuestion() {
        Quiz createdQuiz = createQuiz();

        QuizHandlerObject quiz = new QuizHandlerObject();
        quiz.setQuestion(createdQuiz.getQuestion());

        Assertions.assertThrows(QuestionException.class, () -> {
            javaQuizHandler.create(quiz);
        });
    }

    @Test
    public void testDeleteQuiz() {
        Quiz createdQuiz = createQuiz();
        javaQuizHandler.delete(createdQuiz.getId());
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

        List<String> actualQuestions = actualQuiz.stream().map(Quiz::getQuestion).toList();
        Assertions.assertTrue(actualQuestions.contains(createdQuiz1.getQuestion()));
        Assertions.assertTrue(actualQuestions.contains(createdQuiz2.getQuestion()));
    }

    @Test
    public void testCorrectAnswer() {
        Quiz createdQuiz = createQuiz();

        String userAnswer = "This is mock User answer";
        String gptAnswer = "This is Mock. Test User answer is correct";
        QuizHandlerObject checkQuiz = new QuizHandlerObject();
        checkQuiz.setQuestionId(createdQuiz.getId());
        checkQuiz.setQuestion(createdQuiz.getQuestion());
        checkQuiz.setUserAnswer(userAnswer);

        doAnswer(invocationOnMock -> gptAnswer).when(gptFactory).request(checkQuiz);
        Assertions.assertEquals(gptAnswer, javaQuizHandler.checkAnswer(checkQuiz));
    }

    private Quiz createQuiz() {
        Random random = new Random();
        String expectedQuestion = "test Q" + random.nextInt();
        String expectedAnswer = "test A" + random.nextInt();

        QuizHandlerObject quiz = new QuizHandlerObject();
        quiz.setQuestion(expectedQuestion);

        doAnswer(invocationOnMock -> expectedAnswer).when(gptFactory).request(quiz);

        return javaQuizHandler.create(quiz);
    }

    @AfterEach
    public void wrap() {
        quizRepository.deleteAll();
        reset(gptFactory);
    }
}
