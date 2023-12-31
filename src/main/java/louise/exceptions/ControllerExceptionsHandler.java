package louise.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionsHandler {
    @ExceptionHandler(QuestionException.class)
    public ResponseEntity<CustomException> handleQuestionAlreadyExistException(QuestionException exception) {
        return new ResponseEntity<>(new CustomException(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LanguageException.class)
    public ResponseEntity<CustomException> handleLanguageNotSupportedException(LanguageException exception) {
        return new ResponseEntity<>(new CustomException(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ChatGptException.class)
    public ResponseEntity<String> handleChatGptException(ChatGptException exception) {
        return new ResponseEntity<>(exception.getMessage(), exception.getStatusCode());
    }
}
