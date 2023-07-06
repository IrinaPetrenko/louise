package louise.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerExcpetions {
    @ExceptionHandler(QuestionException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public @ResponseBody CustomException handleQuestionAlreadyExistException(HttpServletRequest request, QuestionException exception) throws IOException {
        return new CustomException(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public @ResponseBody Map<String, String> handleValidationException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }

    @ExceptionHandler(LanguageException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public @ResponseBody CustomException handleLanguageNotSupportedException(LanguageException exception) {
        return new CustomException(exception.getMessage());
    }
}
