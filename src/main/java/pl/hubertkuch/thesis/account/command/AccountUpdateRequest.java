package pl.hubertkuch.thesis.account.command;

public record AccountUpdateRequest(
        String slug
) {
    public static AccountUpdateRequest fromSlug(String slug) {
        return new AccountUpdateRequest(slug);
    }
}
