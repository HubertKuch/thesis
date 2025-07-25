package pl.hubertkuch.thesis.application.validator;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.hubertkuch.thesis.application.command.ApplicationCreationRequest;
import pl.hubertkuch.thesis.application.configuration.ApplicationConfiguration;
import pl.hubertkuch.thesis.application.configuration.ApplicationDescConfiguration;
import pl.hubertkuch.thesis.application.configuration.ApplicationNameConfiguration;
import pl.hubertkuch.thesis.application.exceptions.ApplicationValidationException;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationValidatorTest {

    private static ApplicationValidator validator;

    @BeforeAll
    public static void setUp() {
        var appNameConf = new ApplicationNameConfiguration();
        var appDescConf = new ApplicationDescConfiguration();

        appNameConf.setMinLength(4);
        appNameConf.setMaxLength(10);

        appDescConf.setMinLength(4);
        appDescConf.setMaxLength(15);

        var conf = new ApplicationConfiguration();

        conf.setName(appNameConf);
        conf.setDesc(appDescConf);

        validator = new ApplicationValidator(conf);
    }

    @Test
    public void shouldBeValidInput() {
        var cmd = new ApplicationCreationRequest("some", "Also desc", null);

        assertDoesNotThrow(() -> validator.validateCreationCmd(cmd));
    }

    @Test
    public void shouldThrow_causeOfTooShortName() {
        var cmd = new ApplicationCreationRequest("sm", "Also desc", null);

        assertThrows(ApplicationValidationException.class, () -> validator.validateCreationCmd(cmd));
    }

    @Test
    public void shouldThrow_causeOfTooLongName() {
        var cmd = new ApplicationCreationRequest("realllllllyyy loooooong", "Also desc", null);

        assertThrows(ApplicationValidationException.class, () -> validator.validateCreationCmd(cmd));
    }

    @Test
    public void shouldThrow_causeOfTooShortDesc() {
        var cmd = new ApplicationCreationRequest("some", "sm", null);

        assertThrows(ApplicationValidationException.class, () -> validator.validateCreationCmd(cmd));
    }

    @Test
    public void shouldThrow_causeOfTooLongDesc() {
        var cmd = new ApplicationCreationRequest("some", "probably toooooo loooong desc", null);

        assertThrows(ApplicationValidationException.class, () -> validator.validateCreationCmd(cmd));
    }

}