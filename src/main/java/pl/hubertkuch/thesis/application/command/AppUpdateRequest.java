package pl.hubertkuch.thesis.application.command;

public record AppUpdateRequest(String name, String description) {
    public static AppUpdateRequest of(String name, String description) {
        return new AppUpdateRequest(name, description);
    }
}
