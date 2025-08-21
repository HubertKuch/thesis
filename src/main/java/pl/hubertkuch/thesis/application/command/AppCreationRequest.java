package pl.hubertkuch.thesis.application.command;

import pl.hubertkuch.thesis.account.Account;

public record AppCreationRequest(
        String name,
        String description,
        Account owner
) {
}
