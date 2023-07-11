package louise.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import louise.controller.models.CheckRequest;
import louise.controller.models.CheckResponse;
import louise.controller.models.QuestionRequest;
import louise.controller.models.QuizResponse;
import louise.handler.QuizServiceLocator;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/{language}/quiz")
@RequiredArgsConstructor
public class QuizController {

    private final ResponseConverter responseConverter;
    private final QuizServiceLocator handler;

    @GetMapping("/random")
    public QuizResponse getRandomQuestion(@PathVariable String language) {
        return responseConverter.convert(handler.get(language).getRandom());
    }

    @PostMapping("/new")
    public QuizResponse createNewQuiz(@PathVariable String language,
                                      @RequestBody @Valid QuestionRequest questionRequest) {
        return responseConverter.convert(handler.get(language).create(questionRequest));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String language, @PathVariable long id) {
        handler.get(language).delete(id);
    }

    @GetMapping("/all")
    public List<QuizResponse> getAll(@PathVariable String language) {
        return responseConverter.convert(handler.get(language).getAll());
    }

    @PostMapping("/answer")
    public CheckResponse checkAnswer(@PathVariable String language, @RequestBody @Valid CheckRequest userAnswer) {
        return responseConverter.convert(handler.get(language).checkAnswer(userAnswer));
    }

}
