package pl.hubertkuch.thesis.application.exceptions;

public class ApplicationValidationException extends RuntimeException {
    public ApplicationValidationException(String message) {
        super(message);
    }
}
