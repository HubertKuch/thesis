package pl.hubertkuch.thesis.account.exception;

import pl.hubertkuch.thesis.account.Account;

public class AccountBusyException extends RuntimeException {
    public AccountBusyException() {
        super("State busy");
    }
}
