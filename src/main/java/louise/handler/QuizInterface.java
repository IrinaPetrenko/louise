package louise.handler;

import louise.domain.entities.Quiz;

import java.util.List;

public interface QuizInterface<T, Y> {
    Quiz getRandom();

    Quiz create(T questionRequest);

    void delete(long id);

    List<Quiz> getAll();

    String checkAnswer(Y userAnswer);
}
