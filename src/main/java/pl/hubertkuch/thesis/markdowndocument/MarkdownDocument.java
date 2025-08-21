package pl.hubertkuch.thesis.markdowndocument;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.hubertkuch.thesis.application.Application;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "markdown_document", uniqueConstraints = {@UniqueConstraint(name = "uk_md_app_slug", columnNames = {"application_id", "slug"})}, indexes = {@Index(name = "idx_md_app", columnList = "application_id"), @Index(name = "idx_md_app_published", columnList = "application_id, published, published_at")})
public class MarkdownDocument {

    @Id
    private String id = UUID.randomUUID().toString();

    @Column(nullable = false, length = 255)
    private String title;

    @Column(length = 512)
    private String description;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, length = 150)
    private String slug;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Application application;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "markdown_document_tag", joinColumns = @JoinColumn(name = "markdown_document_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"), indexes = {@Index(name = "idx_md_tag_md", columnList = "markdown_document_id"), @Index(name = "idx_md_tag_tag", columnList = "tag_id")})
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Tag> tags = new HashSet<>();

    @Column(nullable = false)
    private boolean published = false;

    @Column(name = "published_at")
    private Instant publishedAt;

    @Column(length = 1024)
    private String summary;

    @Column(nullable = false)
    private boolean deleted = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at")
    private Instant updatedAt = Instant.now();

    @Version
    private Long version;

    @PrePersist
    protected void onCreate() {
        var now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;

        normaliseSlug();

        if (this.published && this.publishedAt == null) {
            this.publishedAt = now;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();

        normaliseSlug();

        if (this.published && this.publishedAt == null) {
            this.publishedAt = Instant.now();
        }
    }

    public MarkdownDocument addTag(Tag tag) {
        this.tags.add(tag);
        return this;
    }

    public MarkdownDocument removeTag(Tag tag) {
        this.tags.remove(tag);
        return this;
    }

    public MarkdownDocument publish() {
        this.published = true;

        if (this.publishedAt == null) this.publishedAt = Instant.now();

        return this;
    }

    public MarkdownDocument unpublish() {
        this.published = false;
        this.publishedAt = null;
        return this;
    }

    private void normaliseSlug() {
        if (this.slug == null) return;
        this.slug = this.slug.trim().toLowerCase().replaceAll("[^a-z0-9\\-]+", "-");
        if (this.slug.length() > 150) this.slug = this.slug.substring(0, 150);
    }
}
