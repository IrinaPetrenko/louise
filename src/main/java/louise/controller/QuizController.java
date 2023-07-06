package louise.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import louise.controller.models.CheckRequest;
import louise.controller.models.CheckResponse;
import louise.controller.models.QuestionRequest;
import louise.controller.models.QuizResponse;
import louise.handler.QuizHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/{language}/quiz")
public class QuizController {

    @Autowired
    private ResponseConverter responseConverter;
    @Autowired
    @Qualifier("quizHandlerFactory")
    private QuizHandler handler;

    @GetMapping("/random")
    public QuizResponse getRandomQuestion(@PathVariable String language) {
        return responseConverter.convert(handler.getHandler(language).getRandom());
    }

    @PostMapping("/new")
    public QuizResponse createNewQuiz(@PathVariable String language,
                                      @RequestBody @Valid QuestionRequest questionRequest) {
        return responseConverter.convert(handler.getHandler(language).create(questionRequest));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String language, @PathVariable long id) {
        handler.getHandler(language).delete(id);
    }

    @GetMapping("/all")
    public List<QuizResponse> getAll(@PathVariable String language) {
        return responseConverter.convert(handler.getHandler(language).getAll());
    }

    @PostMapping("/answer")
    public CheckResponse checkAnswer(@PathVariable String language, @RequestBody @Valid CheckRequest userAnswer) {
        return responseConverter.convert(handler.getHandler(language).checkAnswer(userAnswer));
    }

}
