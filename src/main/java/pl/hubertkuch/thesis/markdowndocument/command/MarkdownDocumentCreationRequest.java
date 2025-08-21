package pl.hubertkuch.thesis.markdowndocument.command;

import java.util.List;

public record MarkdownDocumentCreationRequest(
        String title,
        String description,
        String content,
        String slug,              // optional: if null, service will generate from title
        List<TagRef> tagNames,    // optional: names of tags to attach (create if missing)
        boolean published,        // whether to publish immediately
        String summary            // optional: override summary; if null service may derive it
) {}
