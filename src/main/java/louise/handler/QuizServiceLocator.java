package louise.handler;

import louise.exceptions.LanguageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class QuizServiceLocator {

    private Map<Languages, QuizInterface> handlers = new HashMap<>();

    public QuizServiceLocator(@Autowired List<QuizInterface> languages) {
        languages.forEach((handler) -> {
            handlers.put(handler.getLanguage(), new QuizHandlerAdapter(handler));
        }
        );
    }
    public QuizInterface get(String language) {
        return handlers.get(Languages.get(language)
                .orElseThrow(() -> new LanguageException(language)));
    }
}
