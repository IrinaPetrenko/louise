package louise.controller.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CheckResponse(@JsonProperty("response") String response) {}
