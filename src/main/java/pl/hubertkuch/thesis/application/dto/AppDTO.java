package pl.hubertkuch.thesis.application.dto;

import pl.hubertkuch.thesis.account.dto.AccountDTO;

public record AppDTO(
        String name,
        String description,
        AccountDTO account
) {
}
