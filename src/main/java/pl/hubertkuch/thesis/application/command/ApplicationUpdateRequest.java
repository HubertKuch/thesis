package pl.hubertkuch.thesis.application.command;

public record ApplicationUpdateRequest(String name, String description) {
    public static ApplicationUpdateRequest of(String name, String description) {
        return new ApplicationUpdateRequest(name, description);
    }
}
