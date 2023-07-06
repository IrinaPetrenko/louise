package louise.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

public class DocumentConverterTest {

    private DocumentConverter documentConverter = new DocumentConverter();

    @Test
    public void testConverter() {
        String question = "testQ";
        String answer = "testA";
        long id = 1640000000;

        DocumentConverter documentConverterSpy = spy(documentConverter);
        doReturn(id).when(documentConverterSpy).createId();

        documentConverterSpy.convert("testQ", "testA");
        Assertions.assertEquals(new Document(id, question, answer), documentConverterSpy.convert("testQ", "testA"));
    }
}
