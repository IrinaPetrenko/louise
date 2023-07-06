package louise.handler;

import lombok.extern.slf4j.Slf4j;
import louise.exceptions.LanguageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class QuizHandlerFactory implements QuizHandler {

    @Autowired
    private JavaQuizHandler javaQuizHandler;

    @Override
    public QuizInterface getHandler(String language) {
        if ("java".equals(language)) {
            return new QuizHandlerDecorator(javaQuizHandler);
        } else {
            throw new LanguageException("Provided language - " + language + " is not supported.");
        }
    }
}
