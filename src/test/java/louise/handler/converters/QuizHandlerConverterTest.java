package louise.handler.converters;

import louise.TestSetup;
import louise.controller.models.CheckRequest;
import louise.controller.models.QuestionRequest;
import louise.handler.entity.QuizHandlerObject;
import org.junit.Assert;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
public class QuizHandlerConverterTest extends TestSetup {

    private QuizHandlerConverter quizHandlerConverter = new QuizHandlerConverter();

    @TestFactory
    Stream<DynamicTest> testQuizHandlerQuestionReqConverter() {
        List<QuestionRequest> inputList = Arrays.asList(
                buildQuestionRequest("test Question"),
                buildQuestionRequest("")
        );

        List<QuizHandlerObject> outputList = Arrays.asList(
                buildQuizHandler("test Question"),
                buildQuizHandler("")
        );

        return inputList.stream()
                .map(dom -> DynamicTest.dynamicTest("testing converter",
                        () -> {
                            int i = inputList.indexOf(dom);
                            Assert.assertEquals(quizHandlerConverter.convert(dom), outputList.get(i));
                        }));
    }

    @TestFactory
    Stream<DynamicTest> testQuizHandlerCheckReqConverter() {
        List<CheckRequest> inputList = Arrays.asList(
                buildCheckRequest(12345, "test User answer"),
                buildCheckRequest(12345, "")
        );

        List<QuizHandlerObject> outputList = Arrays.asList(
                buildQuizHandler(null, 12345, "test User answer"),
                buildQuizHandler(null, 12345, "")
        );

        return inputList.stream()
                .map(dom -> DynamicTest.dynamicTest("testing converter",
                        () -> {
                            int i = inputList.indexOf(dom);
                            Assert.assertEquals(quizHandlerConverter.convert(dom), outputList.get(i));
                        }));
    }
}
