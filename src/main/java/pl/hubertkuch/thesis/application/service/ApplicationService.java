package pl.hubertkuch.thesis.application.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pl.hubertkuch.thesis.application.Application;
import pl.hubertkuch.thesis.application.command.ApplicationCreationRequest;
import pl.hubertkuch.thesis.application.exceptions.ApplicationAlreadyExistsException;
import pl.hubertkuch.thesis.application.repo.ApplicationRepo;
import pl.hubertkuch.thesis.application.validator.ApplicationValidator;

@Service
@Validated
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepo repo;
    private final ApplicationValidator validator;

    @Transactional
    public Application createApplication(@Valid ApplicationCreationRequest cmd) {
        // check for existing that application for that owner
        var maybeApp = repo.findByNameAndOwner(cmd.name(), cmd.owner());

        if (maybeApp.isPresent()) {
            throw new ApplicationAlreadyExistsException();
        }

        // validate
        validator.validateCreationCmd(cmd);

        // save application
        var app = Application.create(cmd);

        return repo.save(app);
    }


}
