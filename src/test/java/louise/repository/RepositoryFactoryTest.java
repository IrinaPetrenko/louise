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
public class RepositoryFactoryTest extends MongoTestSetup {

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    RepositoryFactory repositoryFactory;

    @AfterEach
    public void cleanUp() {
        quizRepository.deleteAll();
    }

    @Test
    public void testCheckQuizAlreadyExists() {
        String question = "test";

        Assertions.assertDoesNotThrow(() -> repositoryFactory.checkQuizAlreadyExists(question));
    }

    @Test
    public void testCheckQuizAlreadyExistsException() {
        String question = "test Q";
        long id = 12345;

        quizRepository.save(new Document(id, question, null));

        Assertions.assertThrows(QuestionException.class, () -> repositoryFactory.checkQuizAlreadyExists(question));
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
        Assertions.assertEquals(expectedDoc, repositoryFactory.checkAndGetQuiz(id));
    }

    @Test
    public void testCheckAndGetQuizNotFound() {
        long id = 12345;

        Assertions.assertThrows(QuestionException.class, () -> repositoryFactory.checkAndGetQuiz(id));
    }

    @Test
    public void testSave() {
        String question = "test Q";
        String answer = "test Answer";

        repositoryFactory.save(question, answer);

        Document actual = repositoryFactory.findAll().get(0);
        Assertions.assertEquals(question, actual.getQuestion());
        Assertions.assertEquals(answer, actual.getAnswer());
    }

    @Test
    public void testSaveEmptyAnswer() {
        String question = "test Q";
        String answer = "";

        repositoryFactory.save(question, answer);

        Document actual = repositoryFactory.findAll().get(0);
        Assertions.assertEquals(question, actual.getQuestion());
        Assertions.assertEquals(answer, actual.getAnswer());
    }

    @Test
    public void testDeleteOneExistingId() {
        String question = "test Q";
        long id = 12345;

        quizRepository.save(new Document(id, question, null));
        List<Document> documents = repositoryFactory.findAll();
        Assertions.assertEquals(1, documents.size());
        repositoryFactory.delete(id);
        Assertions.assertTrue(repositoryFactory.findAll().isEmpty());
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
        List<Document> documents = repositoryFactory.findAll();
        Assertions.assertEquals(2, documents.size());
        repositoryFactory.delete(id1);
        List<Document> actualDocuments = repositoryFactory.findAll();
        Assertions.assertEquals(doc2, actualDocuments.get(0));
    }

    @Test
    public void testDeletingNotExistingId() {
        String question1 = "test Q1";
        long id1 = 12345;
        Document doc1 = new Document(id1, question1, null);
        quizRepository.save(doc1);
        Assertions.assertEquals(1, repositoryFactory.findAll().size());
        repositoryFactory.delete(9999);
        Assertions.assertEquals(1, repositoryFactory.findAll().size());
    }

    @Test
    public void testFindAllOnEmptyDb() {
        Assertions.assertTrue(repositoryFactory.findAll().isEmpty());
    }

    @Test
    public void testFindAllOn1Quiz() {
        String question1 = "test Q1";
        long id1 = 12345;
        Document doc1 = new Document(id1, question1, null);
        quizRepository.save(doc1);
        Assertions.assertEquals(1, repositoryFactory.findAll().size());
        Assertions.assertEquals(doc1, repositoryFactory.findAll().get(0));
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
        Assertions.assertEquals(2, repositoryFactory.findAll().size());
        Assertions.assertEquals(doc1, repositoryFactory.checkAndGetQuiz(id1));
        Assertions.assertEquals(doc2, repositoryFactory.checkAndGetQuiz(id2));
    }

    @Test
    public void testGetRandomWith1Quiz() {
        String question1 = "test Q1";
        long id1 = 12345;
        Document doc1 = new Document(id1, question1, null);
        quizRepository.save(doc1);

        Assertions.assertEquals(doc1, repositoryFactory.getRandom());
    }

    @Test
    public void testGetRandomWithEmptyRepo() {
        Assertions.assertThrows(QuestionException.class, () -> repositoryFactory.getRandom());
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

        Document random = repositoryFactory.getRandom();
        Assertions.assertTrue(doc1.equals(random) || doc2.equals(random));
        Document random1 = repositoryFactory.getRandom();
        Assertions.assertTrue(doc1.equals(random1) || doc2.equals(random1));
    }
}

