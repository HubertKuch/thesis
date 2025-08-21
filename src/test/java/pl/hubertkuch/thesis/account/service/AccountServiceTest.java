package pl.hubertkuch.thesis.account.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import pl.hubertkuch.thesis.OnDbTest;
import pl.hubertkuch.thesis.account.Account;
import pl.hubertkuch.thesis.account.command.AccountCreationRequest;
import pl.hubertkuch.thesis.account.command.AccountUpdateRequest;
import pl.hubertkuch.thesis.account.exception.AccountBusyException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Import(AccountService.class)
class AccountServiceTest extends OnDbTest {

    @Autowired
    private AccountService accountService;

    @BeforeEach
    void cleanDatabase() {
        transactionTemplate.executeWithoutResult(
                s -> em.getEntityManager().createQuery("DELETE FROM Account").executeUpdate()
        );
    }

    @Test
    public void shouldCreateNewAccount() {
        var name = "name";

        var cmd = new AccountCreationRequest(name, "slug", "validemail@test.com");

        var account = accountService.createAccount(cmd);
        var dto = transactionTemplate.execute(
                s -> em.find(Account.class, account.getId()).dto()
        );

        assertNotNull(dto);
        assertEquals(name, dto.name());
    }

    @Test
    public void shouldThrowBusyException_causeOfBusyNameOrSlugOrEmail() {
        var cmd = new AccountCreationRequest("name", "slug", "validemail@test.com");

        accountService.createAccount(cmd);

        assertThrows(AccountBusyException.class, () -> accountService.createAccount(cmd));
    }

    @Test
    public void shouldUpdateExistingAccount() {
        var cmd = new AccountCreationRequest("name", "slug", "validemail@test.com");
        var newAccount = accountService.createAccount(cmd);
        var updatedAccount = accountService.updateAccount(UUID.fromString(newAccount.getId()), AccountUpdateRequest.fromSlug("new-slug"));

        assertNotEquals(newAccount.getSlug(), updatedAccount.getSlug());
        assertEquals(newAccount.getName(), updatedAccount.getName());
        assertEquals("new-slug", updatedAccount.getSlug());
    }

    @Test
    public void shouldDoNotThrowException_edgeCaseWhenSlugIsBusyButRequesterIsOwnerOfThatSlug() {
        var cmd = new AccountCreationRequest("name", "slug", "validemail@test.com");
        var newAccount = accountService.createAccount(cmd);
        var updatedAccount = accountService.updateAccount(UUID.fromString(newAccount.getId()), AccountUpdateRequest.fromSlug("slug"));

        assertEquals("slug", updatedAccount.getSlug());
    }

    @Test
    public void shouldThrowExceptionWhenSlugIsBusyByOtherAccount() {
        var cCmd1 = new AccountCreationRequest("name1", "slug1", "validemail@test.com1");
        var newAccount1 = accountService.createAccount(cCmd1);

        var cCmd2 = new AccountCreationRequest("name2", "slug2", "validemail@test.com2");
        var uCmd2 = AccountUpdateRequest.fromSlug("slug1");
        var newAccount2 = accountService.createAccount(cCmd2);

        assertNotEquals(newAccount1.getSlug(), newAccount2.getSlug());

        assertThrows(AccountBusyException.class, () -> {
            accountService.updateAccount(UUID.fromString(newAccount2.getId()), uCmd2);
        });
    }
}