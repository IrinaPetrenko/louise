package louise;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import louise.controller.models.CheckRequest;
import louise.controller.models.CheckResponse;
import louise.controller.models.QuestionRequest;
import louise.controller.models.QuizResponse;
import louise.domain.GptHandler;
import louise.exceptions.ChatGptException;
import louise.exceptions.CustomException;
import louise.exceptions.QuestionException;
import louise.repository.QuizRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestClientResponseException;
import org.testcontainers.shaded.com.fasterxml.jackson.core.type.TypeReference;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Random;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class QuizControllerTest extends TestSetup {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GptHandler gptHandler;

    @Autowired
    public QuizRepository quizRepository;

    @BeforeEach
    public void prep() {
        log.info("Clearing DB before test...");
        quizRepository.deleteAll();
    }

    @SneakyThrows
    @Test
    public void testCreateNewQuiz() {
        String question = "Is this a controller test?";
        String answer = "Yes, it is.";

        doAnswer(invocationOnMock -> answer).when(gptHandler).request(any());

        QuestionRequest request = new QuestionRequest(question);

        QuizResponse expectedResponse = new QuizResponse(123234, question, answer);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/java/quiz/new")
                        .content(new ObjectMapper().writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    QuizResponse actualResponse = new ObjectMapper().readValue(result.getResponse().getContentAsString(), QuizResponse.class);
                    Assertions.assertEquals(actualResponse.getQuestion(), expectedResponse.getQuestion());
                    Assertions.assertEquals(actualResponse.getAnswer(), expectedResponse.getAnswer());
                });
    }

    @SneakyThrows
    @Test
    public void testCreateNewQuizBadFormat() {
        QuestionRequest request = new QuestionRequest("");

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/java/quiz/new")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is(400));
    }

    @SneakyThrows
    @Test
    public void testGetRandomOnEmpty() {
        this.mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/java/quiz/random")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andExpect(result1 -> {
                    Assertions.assertEquals(new ObjectMapper().readValue(result1.getResponse().getContentAsString(), CustomException.class),
                            new CustomException("Repository is empty. No Quiz found."));
                });
    }

    @SneakyThrows
    @Test
    public void testGetRandom() {
        QuizResponse createdQuiz = createQuiz();

        this.mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/java/quiz/random")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    QuizResponse actualResponse = new ObjectMapper().readValue(result.getResponse().getContentAsString(), QuizResponse.class);
                    Assertions.assertEquals(createdQuiz, actualResponse);
                });
    }

    @Test
    @SneakyThrows
    public void testDeleteExising() {
        QuizResponse createdQuiz = createQuiz();

        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete(String.format("/java/quiz/%s", createdQuiz.getId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(204));

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/java/quiz/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    List<QuizResponse> res = new ObjectMapper().readValue(result.getResponse().getContentAsString(),
                            new TypeReference<List<QuizResponse>>() {
                            });
                    Assertions.assertEquals(0, res.size());
                });
    }

    @Test
    @SneakyThrows
    public void testDeleteNotExisting() {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/java/quiz/848484")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof QuestionException))
                .andExpect(result -> Assertions.assertEquals(result.getResolvedException().getMessage(),
                        "Quiz not found. Check provided id"));
    }

    @Test
    @SneakyThrows
    public void getAllOnSingleQuiz() {
        QuizResponse createdQuiz = createQuiz();

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/java/quiz/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    List<QuizResponse> questions = new ObjectMapper().readValue(result.getResponse().getContentAsString(),
                            new TypeReference<List<QuizResponse>>() {
                            });
                    Assertions.assertEquals(1, questions.size());
                    Assertions.assertEquals(createdQuiz, questions.get(0));
                });
    }

    @Test
    @SneakyThrows
    public void testGetAllOnFewQuiz() {
        QuizResponse createdQuiz1 = createQuiz();
        QuizResponse createdQuiz2 = createQuiz();

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/java/quiz/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    List<QuizResponse> questions = new ObjectMapper().readValue(result.getResponse().getContentAsString(),
                            new TypeReference<List<QuizResponse>>() {
                            });
                    Assertions.assertEquals(2, questions.size());
                    Assertions.assertTrue(questions.contains(createdQuiz1));
                    Assertions.assertTrue(questions.contains(createdQuiz2));
                });
    }

    @Test
    @SneakyThrows
    public void testUserAnswer() {
        QuizResponse createdQuiz = createQuiz();

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/java/quiz/answer")
                        .content(new ObjectMapper().writeValueAsString(prepCheckRequest(createdQuiz.getId())))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    CheckResponse actualResponse = new ObjectMapper().readValue(result.getResponse().getContentAsString(),
                            CheckResponse.class);
                    CheckResponse expectedResponse = new CheckResponse(createdQuiz.getAnswer());
                    Assertions.assertEquals(expectedResponse, actualResponse);
                });
    }

    @SneakyThrows
    @Test
    public void testChatGptException() {
        QuestionRequest request = new QuestionRequest("what is Exception?");

        RestClientResponseException clientException = new RestClientResponseException(
                "test exception",
                HttpStatusCode.valueOf(401),
                "Unauthorized",
                null,
                null,
                null
        );
        ChatGptException exception = new ChatGptException(clientException);

        when(gptHandler.request(any())).thenThrow(exception);

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/java/quiz/new")
                        .content(new ObjectMapper().writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is(401));
    }

    @SneakyThrows
    private QuizResponse createQuiz() {
        Random random = new Random();
        String question = "Is this a controller test?" + random.nextInt();
        String answer = "Yes, it is." + random.nextInt();

        doAnswer(invocationOnMock -> answer).when(gptHandler).request(any());

        QuestionRequest request = new QuestionRequest(question);

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/java/quiz/new")
                        .content(new ObjectMapper().writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk()).
                andReturn();

        return new ObjectMapper().readValue(result.getResponse().getContentAsString(), QuizResponse.class);
    }


    private CheckRequest prepCheckRequest(long quizId) {
        return new CheckRequest(quizId, "This is test User Answer");
    }
}
