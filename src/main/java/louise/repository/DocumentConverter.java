package louise.repository;

import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class DocumentConverter {

    public Document convert(String question, String answer) {
        Document document = new Document();
        /**
         * TODO: Why do we need to set the id here? why not to leave it to the database?
         */
        document.setId(createId());
        document.setQuestion(question);
        document.setAnswer(answer);
        return document;
    }

    protected long createId() {
        /**
         * TODO:What if we receive 2 requests in the same exact millisecond? (yes it can happen)
         */
        return Instant.now().toEpochMilli();
    }
}
