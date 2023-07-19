package louise.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import louise.domain.GptHandler;
import louise.domain.QuizConverter;
import louise.domain.entities.Quiz;
import louise.exceptions.QuestionException;
import louise.handler.entity.QuizHandlerObject;
import louise.repository.DocumentService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class JavaQuizHandler implements QuizInterface<QuizHandlerObject, QuizHandlerObject> {
    private final QuizConverter quizConverter;
    private final DocumentService documentService;
    private final GptHandler gptHandler;

    @Override
    public Quiz getRandom() {
        return quizConverter.convert(documentService.getRandom());
    }

    @Override
    public Quiz create(QuizHandlerObject request) throws QuestionException {
        documentService.existsBy(request.getQuestion());
        return quizConverter.convert(documentService.save(
                        request.getQuestion(),
                        gptHandler.request(request)
                )
        );
    }

    @Override
    /**
     * TODO: Can we refactor this method to have just delete? for example documentService.delete(id)
     */
    public void delete(long id) {
        documentService.findBy(id);
        documentService.delete(id);
    }

    @Override
    public List<Quiz> getAll() {
        return documentService.findAll().stream().map(quizConverter::convert).toList();
    }

    @Override
    public String checkAnswer(QuizHandlerObject request) {
        documentService.findBy(request.getQuestionId());
        return gptHandler.request(request);
    }

    @Override
    public Languages getLanguage() {
        return Languages.JAVA;
    }
}
