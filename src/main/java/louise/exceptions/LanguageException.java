package louise.exceptions;

public class LanguageException extends RuntimeException {

    public LanguageException(String language) {

        super("Provided language - " + language + " is not supported.");
    }
}
