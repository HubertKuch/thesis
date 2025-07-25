package pl.hubertkuch.thesis.application.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.hubertkuch.thesis.application.command.ApplicationCreationRequest;
import pl.hubertkuch.thesis.application.configuration.ApplicationConfiguration;
import pl.hubertkuch.thesis.application.exceptions.ApplicationValidationException;

@Component
@RequiredArgsConstructor
public class ApplicationValidator {
    private final ApplicationConfiguration conf;

    /**
     * @throws pl.hubertkuch.thesis.application.exceptions.ApplicationValidationException when not valid
     */
    public void validateCreationCmd(ApplicationCreationRequest cmd) {
        if (!validateName(cmd.name())) {
            throw new ApplicationValidationException("`%s` name is not valid. Length should be between %d to %d".formatted(cmd.name(), conf.getName().getMinLength(), conf.getName().getMaxLength()));
        }

        if (!validateDesc(cmd.description())) {
            throw new ApplicationValidationException("`%s` description is not valid. Length should be between %d to %d".formatted(cmd.name(), conf.getDesc().getMinLength(), conf.getDesc().getMaxLength()));
        }
    }

    private boolean validateName(String name) {
        return name.length() >= conf.getName().getMinLength() && name.length() <= conf.getName().getMaxLength();
    }

    private boolean validateDesc(String desc) {
        return desc.length() >= conf.getDesc().getMinLength() && desc.length() <= conf.getDesc().getMaxLength();
    }
}
