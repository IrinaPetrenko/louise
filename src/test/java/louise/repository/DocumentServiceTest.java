package louise.repository;

import lombok.extern.slf4j.Slf4j;
import louise.MongoTestSetup;
import louise.exceptions.QuestionException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class DocumentServiceTest extends MongoTestSetup {

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    DocumentService documentService;

    @AfterEach
    public void cleanUp() {
        quizRepository.deleteAll();
    }

    @Test
    public void testCheckQuizAlreadyExists() {
        String question = "test";

        Assertions.assertDoesNotThrow(() -> documentService.existsBy(question));
    }

    @Test
    public void testCheckQuizAlreadyExistsException() {
        String question = "test Q";
        long id = 12345;

        quizRepository.save(new Document(id, question, null));

        Assertions.assertThrows(QuestionException.class, () -> documentService.existsBy(question));
    }

    @Test
    public void testCheckAndGetQuizFound() {
        String question = "test Q";
        long id = 12345;
        Document expectedDoc = new Document();
        expectedDoc.setAnswer(null);
        expectedDoc.setQuestion(question);
        expectedDoc.setId(id);

        quizRepository.save(new Document(id, question, null));
        Assertions.assertEquals(expectedDoc, documentService.findBy(id));
    }

    @Test
    public void testCheckAndGetQuizNotFound() {
        long id = 12345;

        Assertions.assertThrows(QuestionException.class, () -> documentService.findBy(id));
    }

    @Test
    public void testSave() {
        String question = "test Q";
        String answer = "test Answer";

        documentService.save(question, answer);

        Document actual = documentService.findAll().get(0);
        Assertions.assertEquals(question, actual.getQuestion());
        Assertions.assertEquals(answer, actual.getAnswer());
    }

    @Test
    public void testSaveEmptyAnswer() {
        String question = "test Q";
        String answer = "";

        documentService.save(question, answer);

        Document actual = documentService.findAll().get(0);
        Assertions.assertEquals(question, actual.getQuestion());
        Assertions.assertEquals(answer, actual.getAnswer());
    }

    @Test
    public void testDeleteOneExistingId() {
        String question = "test Q";
        long id = 12345;

        quizRepository.save(new Document(id, question, null));
        List<Document> documents = documentService.findAll();
        Assertions.assertEquals(1, documents.size());
        documentService.delete(id);
        Assertions.assertTrue(documentService.findAll().isEmpty());
    }

    @Test
    public void testDeleteExistingId() {
        String question1 = "test Q1";
        long id1 = 12345;
        String question2 = "test Q2";
        long id2 = 23456;
        Document doc1 = new Document(id1, question1, null);
        Document doc2 = new Document(id2, question2, null);

        quizRepository.save(doc1);
        quizRepository.save(doc2);
        List<Document> documents = documentService.findAll();
        Assertions.assertEquals(2, documents.size());
        documentService.delete(id1);
        List<Document> actualDocuments = documentService.findAll();
        Assertions.assertEquals(doc2, actualDocuments.get(0));
    }

    @Test
    public void testDeletingNotExistingId() {
        String question1 = "test Q1";
        long id1 = 12345;
        Document doc1 = new Document(id1, question1, null);
        quizRepository.save(doc1);
        Assertions.assertEquals(1, documentService.findAll().size());
        documentService.delete(9999);
        Assertions.assertEquals(1, documentService.findAll().size());
    }

    @Test
    public void testFindAllOnEmptyDb() {
        Assertions.assertTrue(documentService.findAll().isEmpty());
    }

    @Test
    public void testFindAllOn1Quiz() {
        String question1 = "test Q1";
        long id1 = 12345;
        Document doc1 = new Document(id1, question1, null);
        quizRepository.save(doc1);
        Assertions.assertEquals(1, documentService.findAll().size());
        Assertions.assertEquals(doc1, documentService.findAll().get(0));
    }

    @Test
    public void testFindAllOn2Quiz() {
        String question1 = "test Q1";
        long id1 = 12345;
        String question2 = "test Q2";
        long id2 = 23456;
        Document doc1 = new Document(id1, question1, null);
        Document doc2 = new Document(id2, question2, null);

        quizRepository.save(doc1);
        quizRepository.save(doc2);
        Assertions.assertEquals(2, documentService.findAll().size());
        Assertions.assertEquals(doc1, documentService.findBy(id1));
        Assertions.assertEquals(doc2, documentService.findBy(id2));
    }

    @Test
    public void testGetRandomWith1Quiz() {
        String question1 = "test Q1";
        long id1 = 12345;
        Document doc1 = new Document(id1, question1, null);
        quizRepository.save(doc1);

        Assertions.assertEquals(doc1, documentService.getRandom());
    }

    @Test
    public void testGetRandomWithEmptyRepo() {
        Assertions.assertThrows(QuestionException.class, () -> documentService.getRandom());
    }

    @Test
    public void testGetRandom() {
        String question1 = "test Q1";
        long id1 = 12345;
        String question2 = "test Q2";
        long id2 = 23456;
        Document doc1 = new Document(id1, question1, null);
        Document doc2 = new Document(id2, question2, null);

        quizRepository.save(doc1);
        quizRepository.save(doc2);

        Document random = documentService.getRandom();
        Assertions.assertTrue(doc1.equals(random) || doc2.equals(random));
        Document random1 = documentService.getRandom();
        Assertions.assertTrue(doc1.equals(random1) || doc2.equals(random1));
    }
}

