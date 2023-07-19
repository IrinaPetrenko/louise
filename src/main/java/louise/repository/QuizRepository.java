package louise.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface QuizRepository extends MongoRepository<Document, Long> {
    /**
     * TODO: Why do we need to use @Query here? why not to use the method name?
     */
    @Query(value = "{question:?0}")
    Document findByQuestion(String question);
}
