package pl.hubertkuch.thesis.markdowndocument.command;

import java.util.List;

public record MarkdownDocumentUpdateRequest(
        String title,             // nullable — when null keep existing
        String description,       // nullable
        String content,           // nullable
        String slug,              // nullable — when null keep existing; when provided must be unique per app
        List<TagRef> tagNames,    // nullable — when null keep existing; empty list means detach all tags
        Boolean published,        // nullable — when null keep existing; true -> set publishedAt if missing
        String summary            // nullable
) {}
