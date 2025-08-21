package pl.hubertkuch.thesis.application.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pl.hubertkuch.thesis.application.Application;
import pl.hubertkuch.thesis.application.command.AppCreationRequest;
import pl.hubertkuch.thesis.application.command.AppUpdateRequest;
import pl.hubertkuch.thesis.application.exceptions.AppNotFoundException;
import pl.hubertkuch.thesis.application.exceptions.AppAlreadyExistsException;
import pl.hubertkuch.thesis.application.repo.AppRepo;
import pl.hubertkuch.thesis.application.validator.AppValidator;

import java.util.Optional;
import java.util.UUID;

@Service
@Validated
@RequiredArgsConstructor
public class AppService {

    private final AppRepo repo;
    private final AppValidator validator;

    @Transactional
    public Application createApplication(@Valid AppCreationRequest cmd) {
        // check for existing that application for that owner
        var maybeApp = repo.findByNameAndOwner(cmd.name(), cmd.owner());

        if (maybeApp.isPresent()) {
            throw new AppAlreadyExistsException();
        }

        // validate
        validator.validateCreationCmd(cmd);

        // save application
        var app = Application.create(cmd);

        return repo.save(app);
    }

    @Transactional
    public Application updateAccount(UUID uuid, AppUpdateRequest command) {
        var maybeApp = this.findById(uuid);

        // is app exists
        if (maybeApp.isEmpty()) {
            throw new AppNotFoundException();
        }

        var app = maybeApp.get();

        app.update(command);

        return repo.save(app);
    }

    private Optional<Application> findById(UUID uuid) {
        return repo.findById(uuid.toString());
    }
}
