package pl.hubertkuch.thesis.markdowndocument.command;

public record TagUpdateRequest(
        String name,   // nullable — when null, keep existing
        String color   // nullable — when null, keep existing; use valid hex when provided
) {}
