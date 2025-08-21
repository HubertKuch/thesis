package pl.hubertkuch.thesis.markdowndocument;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

@Data
@Entity
@Table(name = "tag",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_tag_name", columnNames = {"name"})
        },
        indexes = {
                @Index(name = "idx_tag_name", columnList = "name")
        })
public class Tag {

    @Id
    private String id = UUID.randomUUID().toString();

    @Column(nullable = false, length = 100)
    private String name;

    /**
     * Hex color string, normalized to lowercase and including leading '#', e.g. "#1a2b3c".
     * Nullable.
     */
    @Column(length = 7)
    private String color;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<MarkdownDocument> documents = new HashSet<>();

    private static final Pattern HEX_COLOR = Pattern.compile("^#([a-f0-9]{6})$");

    @PrePersist
    @PreUpdate
    protected void normalizeFields() {
        normalizeName();
        normalizeColor();
    }

    protected void normalizeName() {
        if (this.name != null) {
            this.name = this.name.trim().toLowerCase().replaceAll("\\s+", "-");
            if (this.name.length() > 100) this.name = this.name.substring(0, 100);
        }
    }

    protected void normalizeColor() {
        if (this.color == null || this.color.isBlank()) {
            this.color = null;
            return;
        }
        var c = this.color.trim().toLowerCase();
        if (!c.startsWith("#")) c = "#" + c;
        if (c.length() != 7 || !HEX_COLOR.matcher(c).matches()) {
            throw new IllegalArgumentException("Invalid hex color: " + this.color + ". Expected format: #rrggbb");
        }
        this.color = c;
    }
}
