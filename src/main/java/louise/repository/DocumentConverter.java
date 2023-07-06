package louise.repository;

import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class DocumentConverter {

    public Document convert(String question, String answer) {
        Document document = new Document();
        document.setId(createId());
        document.setQuestion(question);
        document.setAnswer(answer);
        return document;
    }

    protected long createId() {
        return Instant.now().toEpochMilli();
    }
}
