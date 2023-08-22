package louise.domain;

import louise.TestSetup;
import louise.controller.models.QuestionRequest;
import louise.domain.chatGpt.QuestionObject;
import org.junit.Assert;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
public class GptQuestionObjectConverterTest extends TestSetup {

    @InjectMocks
    private GptQuestionObjectConverter gptQuestionObjectConverter = new GptQuestionObjectConverter();

    @TestFactory
    Stream<DynamicTest> testQuestionRequestConverter() {
        List<QuestionRequest> inputList = Arrays.asList(
                buildQuestionRequest("test Question"),
                buildQuestionRequest("")
        );

        List<QuestionObject> outputList = Arrays.asList(
                buildQuestionObject("test Question"),
                buildQuestionObject("")
        );

        return inputList.stream()
                .map(dom -> DynamicTest.dynamicTest("testing converter",
                        () -> {
                            int i = inputList.indexOf(dom);
                            Assert.assertEquals(gptQuestionObjectConverter.convert(dom), outputList.get(i));
                        }));
    }

    @TestFactory
    Stream<DynamicTest> testStringConverter() {
        List<String> inputList = Arrays.asList(
                "test Question",
                ""
        );

        List<QuestionObject> outputList = Arrays.asList(
                buildQuestionObject("test Question"),
                buildQuestionObject("")
        );

        return inputList.stream()
                .map(dom -> DynamicTest.dynamicTest("testing converter",
                        () -> {
                            int i = inputList.indexOf(dom);
                            Assert.assertEquals(gptQuestionObjectConverter.convert(dom), outputList.get(i));
                        }));
    }
}

