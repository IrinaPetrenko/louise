package louise.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import louise.exceptions.LanguageException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class QuizHandlerFactory {
    private final JavaQuizHandler javaQuizHandler;

    public QuizInterface getHandler(String language) {
        if ("java".equals(language)) {
            return new QuizHandlerDecorator(javaQuizHandler);
        } else {
            throw new LanguageException("Provided language - " + language + " is not supported.");
        }
    }
}
