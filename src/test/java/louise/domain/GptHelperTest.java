package louise.domain;

import louise.TestSetup;
import louise.exceptions.QuestionException;
import louise.repository.Document;
import louise.repository.DocumentService;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.reset;

@ExtendWith(MockitoExtension.class)
public class GptHelperTest extends TestSetup {

    private static final String url = "https://test";
    private static final String testQuestion = "test question";
    private static final String testAnswer = "test answer";

    @Mock
    private DocumentService documentService;

    @Mock
    private RestTemplate restTemplate = new RestTemplate();

    @Mock
    private GptQuestionObjectConverter gptQuestionObjectConverter = new GptQuestionObjectConverter();

    @InjectMocks
    private GptHandler gptHandler;

    @BeforeEach
    public void prep() {
        doAnswer(invocation -> buildQuestionObject(testQuestion)).when(gptQuestionObjectConverter).convert(anyString(), eq(mockGptProps));

        doAnswer(invocation -> url).when(mockGptProps).getUrl();
    }

    @AfterEach
    public void wrap() {
        reset(restTemplate);
        reset(gptQuestionObjectConverter);
        reset(mockGptProps);
        reset(documentService);
    }

    @Test
    public void testQuestionWithGptAnswer() {
        doAnswer(invocation -> buildAnswerObject(testAnswer)).when(restTemplate).postForObject(eq(url),
                any(), any());

        String actualAnswer = gptHandler.request(buildQuizHandler(testQuestion));
        Assert.assertEquals(testAnswer, actualAnswer);
    }

    @Test
    public void testQuestionWithEmptyGptAnswer() {
        doAnswer(invocation -> buildAnswerObject("")).when(restTemplate).postForObject(eq(url),
                any(), any());

        String actualAnswer = gptHandler.request(buildQuizHandler(testQuestion));
        Assert.assertEquals("", actualAnswer);
    }

    @Test
    public void testQuestionWithUserAnswerAndGptRequest() {
        String expectedGptAnswer = "this is a correct User answer";
        String userAnswer = "This is test User answer";
        long quizId = 123456;

        doAnswer(invocation -> buildAnswerObject(expectedGptAnswer)).when(restTemplate).postForObject(eq(url),
                any(), any());
        doAnswer(invocation -> new Document(quizId, testQuestion, testAnswer)).when(documentService).findBy(quizId);

        String actualAnswer = gptHandler.request(buildQuizHandler(testQuestion, quizId, userAnswer));
        Assert.assertEquals(expectedGptAnswer, actualAnswer);
    }

    @Test
    public void testQuestionWithUserAnswerQuizNotFound() {
        doAnswer(invocation -> {
            throw new QuestionException("test exception");
        }).when(documentService).findBy((long) 123456);

        Assert.assertThrows(QuestionException.class, () -> {
            gptHandler.request(buildQuizHandler(testQuestion, 123456, "Java is cool"));
        });
    }

    @Test
    public void testQuestionWithUserAnswerAndEmptyGptRequest() {
        doAnswer(invocation -> buildAnswerObject("")).when(restTemplate).postForObject(eq(url),
                any(), any());
        doAnswer(invocation -> new Document(123456, testQuestion, testAnswer)).when(documentService).findBy(123456);

        String actualAnswer = gptHandler.request(buildQuizHandler(testQuestion, 123456, "Java is cool"));
        Assert.assertEquals("", actualAnswer);
    }
}


