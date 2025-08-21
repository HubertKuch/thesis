package pl.hubertkuch.thesis.application.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.hubertkuch.thesis.application.command.AppCreationRequest;
import pl.hubertkuch.thesis.application.configuration.AppsConfig;
import pl.hubertkuch.thesis.application.exceptions.AppValidationException;

@Component
@RequiredArgsConstructor
public class AppValidator {
    private final AppsConfig conf;

    /**
     * @throws AppValidationException when not valid
     */
    public void validateCreationCmd(AppCreationRequest cmd) throws AppValidationException {
        if (!validateName(cmd.name())) {
            throw new AppValidationException("`%s` name is not valid. Length should be between %d to %d".formatted(cmd.name(), conf.getName().getMinLength(), conf.getName().getMaxLength()));
        }

        if (!validateDesc(cmd.description())) {
            throw new AppValidationException("`%s` description is not valid. Length should be between %d to %d".formatted(cmd.name(), conf.getDesc().getMinLength(), conf.getDesc().getMaxLength()));
        }
    }

    private boolean validateName(String name) {
        return name.length() >= conf.getName().getMinLength() && name.length() <= conf.getName().getMaxLength();
    }

    private boolean validateDesc(String desc) {
        return desc.length() >= conf.getDesc().getMinLength() && desc.length() <= conf.getDesc().getMaxLength();
    }
}
