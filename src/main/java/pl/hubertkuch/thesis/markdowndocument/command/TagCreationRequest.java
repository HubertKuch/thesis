package pl.hubertkuch.thesis.markdowndocument.command;

public record TagCreationRequest(
        String name,
        String color // nullable — when null; use valid hex when provided
) {
}
