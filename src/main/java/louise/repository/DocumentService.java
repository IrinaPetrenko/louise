package louise.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import louise.exceptions.QuestionException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class DocumentService {
    private final DocumentConverter documentConverter;
    private final QuizRepository quizRepository;
    public void existsBy(String question) {
        Optional.ofNullable(quizRepository.findByQuestion(question)).ifPresentOrElse(
                (value) -> {
                    throw new QuestionException("Such question already exists");
                },
                () -> log.info("Question does not exist yet")
        );
    }

    public Document findBy(long id) {
        return quizRepository.findById(id).orElseThrow(() -> new QuestionException("Quiz not found. Check provided id"));
    }

    public Document save(String question, String answer) {
        Document document = documentConverter.convert(question, answer);
        quizRepository.save(document);
        return document;
    }

    public void delete(long id) {
        quizRepository.deleteById(id);
    }

    public List<Document> findAll() {
        return quizRepository.findAll();
    }

    public Document getRandom() {
        List<Document> documents = findAll();
        if (documents.isEmpty()) throw new QuestionException("Repository is empty. No Quiz found.");
        Random random = new Random();
        return documents.get(
                random.ints(0, documents.size()).findAny().getAsInt());
    }
}
