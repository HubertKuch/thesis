package pl.hubertkuch.thesis.application.command;

import pl.hubertkuch.thesis.account.Account;

public record ApplicationCreationRequest(String name, String description, Account owner) {
}
