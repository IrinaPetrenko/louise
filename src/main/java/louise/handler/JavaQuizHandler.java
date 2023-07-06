package louise.handler;

import lombok.extern.slf4j.Slf4j;
import louise.domain.GptFactory;
import louise.domain.QuizConverter;
import louise.domain.entities.Quiz;
import louise.exceptions.QuestionException;
import louise.handler.entity.QuizHandlerObject;
import louise.repository.RepositoryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class JavaQuizHandler implements QuizInterface<QuizHandlerObject, QuizHandlerObject> {

    @Autowired
    private QuizConverter quizConverter;
    @Autowired
    private RepositoryFactory repositoryFactory;
    @Autowired
    private GptFactory gptFactory;

    @Override
    public Quiz getRandom() {
        return quizConverter.convert(repositoryFactory.getRandom());
    }

    @Override
    public Quiz create(QuizHandlerObject request) throws QuestionException {
        repositoryFactory.checkQuizAlreadyExists(request.getQuestion());
        return quizConverter.convert(repositoryFactory.save(
                        request.getQuestion(),
                        gptFactory.request(request)
                )
        );
    }

    @Override
    public void delete(long id) {
        repositoryFactory.checkAndGetQuiz(id);
        repositoryFactory.delete(id);
    }

    @Override
    public List<Quiz> getAll() {
        return repositoryFactory.findAll().stream().map(quizConverter::convert).toList();
    }

    @Override
    public String checkAnswer(QuizHandlerObject request) {
        repositoryFactory.checkAndGetQuiz(request.getQuestionId());
        return gptFactory.request(request);
    }
}
