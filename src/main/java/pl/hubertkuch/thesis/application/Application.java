package pl.hubertkuch.thesis.application;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import pl.hubertkuch.thesis.account.Account;
import pl.hubertkuch.thesis.application.command.ApplicationCreationRequest;
import pl.hubertkuch.thesis.application.command.ApplicationUpdateRequest;

import java.util.UUID;

@Data
@Entity
public class Application {
    @Id
    private String id = UUID.randomUUID().toString();
    private String name;
    private String description;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Account owner;

    public static Application create(ApplicationCreationRequest cmd) {
        Application app = new Application();

        app.setName(cmd.name());
        app.setDescription(cmd.description());
        app.setOwner(cmd.owner());

        return app;
    }

    public Application update(ApplicationUpdateRequest cmd) {
        this.name = cmd.name();
        this.description = cmd.description();

        return this;
    }
}
