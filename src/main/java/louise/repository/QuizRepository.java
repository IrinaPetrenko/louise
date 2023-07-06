package louise.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface QuizRepository extends MongoRepository<Document, Long> {
    @Query(value = "{question:?0}")
    Document findByQuestion(String question);
}
