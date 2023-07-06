package louise.domain;

import louise.domain.entities.Quiz;
import louise.repository.Document;
import org.junit.Assert;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class QuizConverterTest {

    private QuizConverter quizConverter = new QuizConverter();

    @TestFactory
    Stream<DynamicTest> testConverter() {
        List<Document> inputList = Arrays.asList(
                new Document(123312, "test question", "test answer"),
                new Document(123312, "", "test answer"),
                new Document(123312, "test question", ""),
                new Document(123312, "", ""),
                new Document(123312, "test question", null)
        );

        List<Quiz> outputList = Arrays.asList(
                new Quiz(123312, "test question", "test answer", null),
                new Quiz(123312, "", "test answer", null),
                new Quiz(123312, "test question", "", null),
                new Quiz(123312, "", "", null),
                new Quiz(123312, "test question", null, null)

        );

        return inputList.stream()
                .map(dom -> DynamicTest.dynamicTest("testing converter",
                        () -> {
                            int i = inputList.indexOf(dom);
                            Assert.assertEquals(quizConverter.convert(dom), outputList.get(i));
                        }));
    }
}