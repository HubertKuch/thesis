package pl.hubertkuch.thesis.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.beans.factory.annotation.Autowired;
import pl.hubertkuch.thesis.OnDbTest;
import pl.hubertkuch.thesis.account.Account;
import pl.hubertkuch.thesis.account.command.AccountCreationRequest;
import pl.hubertkuch.thesis.account.service.AccountService;
import pl.hubertkuch.thesis.application.Application;
import pl.hubertkuch.thesis.application.command.AppCreationRequest;
import pl.hubertkuch.thesis.application.command.AppUpdateRequest;
import pl.hubertkuch.thesis.application.configuration.AppsConfig;
import pl.hubertkuch.thesis.application.exceptions.AppNotFoundException;
import pl.hubertkuch.thesis.application.exceptions.AppAlreadyExistsException;
import pl.hubertkuch.thesis.application.validator.AppValidator;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Import({AppService.class, AccountService.class, AppsConfig.class, AppValidator.class})
class AppServiceTest extends OnDbTest {

    @Autowired
    private AppService appService;

    @Autowired
    private AccountService accountService;

    @BeforeEach
    void cleanDatabase() {
        transactionTemplate.executeWithoutResult(s -> {
            em.getEntityManager().createQuery("DELETE FROM Application").executeUpdate();
            em.getEntityManager().createQuery("DELETE FROM Account").executeUpdate();
        });
    }

    @Test
    void createApplication_success() {
        var descNew = "super turbo desc";

        var accountCmd = new AccountCreationRequest("ownerName", "owner-slug", "owner@example.com");
        var ownerDto = transactionTemplate.execute(s -> accountService.createAccount(accountCmd));

        var createCmd = new AppCreationRequest("MyApp", descNew, em.getEntityManager().find(Account.class, ownerDto.getId()));
        var created = appService.createApplication(createCmd);

        var stored = transactionTemplate.execute(s -> em.find(Application.class, created.getId()));
        assertNotNull(stored);
        assertEquals("MyApp", stored.getName());
        assertEquals(descNew, stored.getDescription());
        assertNotNull(stored.getOwner());
        assertEquals(ownerDto.getId(), stored.getOwner().getId());
    }

    @Test
    void createApplication_whenNameExistsForOwner_shouldThrow() {
        var accountCmd = new AccountCreationRequest("owner2", "owner2-slug", "owner2@example.com");
        var ownerDto = transactionTemplate.execute(s -> accountService.createAccount(accountCmd));

        var ownerEntity = transactionTemplate.execute(s -> em.find(Account.class, ownerDto.getId()));

        var createCmd1 = new AppCreationRequest("DuplicateApp", "super turbo desc 1", ownerEntity);
        appService.createApplication(createCmd1);

        var createCmd2 = new AppCreationRequest("DuplicateApp", "super turbo desc 2", ownerEntity);
        assertThrows(AppAlreadyExistsException.class, () -> appService.createApplication(createCmd2));
    }

    @Test
    void updateApplication_success() {
        var descNew = "super turbo desc new";

        var accountCmd = new AccountCreationRequest("owner3", "owner3-slug", "owner3@example.com");
        var ownerDto = transactionTemplate.execute(s -> accountService.createAccount(accountCmd));
        var ownerEntity = transactionTemplate.execute(s -> em.find(Account.class, ownerDto.getId()));

        var createCmd = new AppCreationRequest("UpdatableApp", "super turbo desc old", ownerEntity);
        var created = appService.createApplication(createCmd);


        var updateCmd = new AppUpdateRequest("UpdatedName", descNew);
        var updated = appService.updateAccount(UUID.fromString(created.getId()), updateCmd);

        assertEquals("UpdatedName", updated.getName());
        assertEquals(descNew, updated.getDescription());

        var stored = transactionTemplate.execute(s -> em.find(Application.class, created.getId()));
        assertEquals("UpdatedName", stored.getName());
        assertEquals(descNew, stored.getDescription());
    }

    @Test
    void updateApplication_whenNotFound_shouldThrow() {
        var randomId = UUID.randomUUID();
        var updateCmd = new AppUpdateRequest("NoApp", "no-desc");
        assertThrows(AppNotFoundException.class, () -> appService.updateAccount(randomId, updateCmd));
    }
}
