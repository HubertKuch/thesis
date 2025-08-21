package pl.hubertkuch.thesis.markdowndocument.command;

/**
 * Hybrid reference for tags: either id (preferred when known) or name (to resolve-or-create).
 * Exactly one of id or name should be non-null when submitted; service will resolve by id first.
 */
public record TagRef(
        String id,
        String name
) {}
