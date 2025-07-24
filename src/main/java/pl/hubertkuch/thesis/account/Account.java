package pl.hubertkuch.thesis.account;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import pl.hubertkuch.thesis.account.command.AccountCreationRequest;
import pl.hubertkuch.thesis.account.command.AccountUpdateRequest;
import pl.hubertkuch.thesis.account.dto.AccountDTO;
import pl.hubertkuch.thesis.account.dto.AccountMapper;

import java.util.UUID;

@Data
@Entity
public class Account {
    @Id
    private String id = UUID.randomUUID().toString();
    private String slug;
    private String name;
    private String email;

    private boolean deleted = false;

    public Account update(AccountUpdateRequest command) {
        this.slug = command.slug();

        return this;
    }

    public AccountDTO dto() {
        return AccountMapper.INSTANCE.accountToDTO(this);
    }

    public static Account create(AccountCreationRequest command) {
        var acc = new Account();

        acc.setEmail(command.email());
        acc.setSlug(command.slug());
        acc.setName(command.name());

        return acc;
    }
}
