package pl.hubertkuch.thesis.account.command;

public record AccountCreationRequest(
        String name,
        String slug,
        String email,
        String password
) {
}
