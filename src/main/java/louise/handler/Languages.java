package louise.handler;

import java.util.Arrays;
import java.util.Optional;

public enum Languages {
    JAVA("java"),
    KOTLIN("kotlin"),
    DEFAULT("default");

    public final String language;

    private Languages(String language) {
        this.language = language;
    }

    public static Optional<Languages> get(String language) {
        return Arrays.stream(Languages.values()).filter(languages ->
                languages.language.equals(language)).findFirst();
    }
}
