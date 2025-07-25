package pl.hubertkuch.thesis.application.command;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import pl.hubertkuch.thesis.account.Account;

public record ApplicationCreationRequest(
        @Min(2) @Max(8)
        String name,
        @Min(2) @Max(8)
        String description,
        @NotNull
        Account owner
) {
}
