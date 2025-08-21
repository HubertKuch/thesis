package pl.hubertkuch.thesis.application.dto;

import pl.hubertkuch.thesis.account.dto.AccountDTO;

public record ApplicationDTO(
        String name,
        String description,
        AccountDTO account
) {
}
