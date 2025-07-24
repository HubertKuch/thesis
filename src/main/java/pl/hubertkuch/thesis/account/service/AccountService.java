package pl.hubertkuch.thesis.account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.hubertkuch.thesis.account.Account;
import pl.hubertkuch.thesis.account.command.AccountCreationRequest;
import pl.hubertkuch.thesis.account.command.AccountUpdateRequest;
import pl.hubertkuch.thesis.account.exception.AccountBusyException;
import pl.hubertkuch.thesis.account.exception.AccountNotFoundException;
import pl.hubertkuch.thesis.account.repo.AccountRepo;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepo repo;

    /**
    * @apiNote creates account if not exists but not validate data. Should be valid because login is via GitHub
    * */
    @Transactional
    public Account createAccount(AccountCreationRequest command) {
        var maybeAccount = repo.findByNameOrEmailOrSlug(command.name(), command.email(), command.slug());

        if (maybeAccount.isPresent()) {
            throw new AccountBusyException();
        }

        var account = Account.create(command);

        return repo.save(account);
    }

    @Transactional
    public Account updateAccount(UUID uuid, AccountUpdateRequest command) {
        var maybeAccount = this.findById(uuid);

        // is account exists
        if (maybeAccount.isEmpty()) {
            throw new AccountNotFoundException();
        }

        var account = maybeAccount.get();

        var bySlug = repo.findBySlug(command.slug());

        // edge case: slug can be busy by user who requested change
        if (bySlug.isPresent() && !UUID.fromString(bySlug.get().getId()).equals(uuid)) {
            throw new AccountBusyException();
        }

        account.update(command);

        return repo.save(account);
    }

    public Optional<Account> findById(UUID uuid) {
        return repo.findById(uuid.toString());
    }

}
