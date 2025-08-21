package pl.hubertkuch.thesis.application.validator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.hubertkuch.thesis.application.command.AppCreationRequest;
import pl.hubertkuch.thesis.application.configuration.AppsConfig;
import pl.hubertkuch.thesis.application.configuration.AppDescConfiguration;
import pl.hubertkuch.thesis.application.configuration.AppNameConfiguration;
import pl.hubertkuch.thesis.application.exceptions.AppValidationException;

import static org.junit.jupiter.api.Assertions.*;

class AppValidatorTest {

    private static AppValidator validator;

    @BeforeAll
    public static void setUp() {
        var appNameConf = new AppNameConfiguration();
        var appDescConf = new AppDescConfiguration();

        appNameConf.setMinLength(4);
        appNameConf.setMaxLength(10);

        appDescConf.setMinLength(4);
        appDescConf.setMaxLength(15);

        var conf = new AppsConfig();

        conf.setName(appNameConf);
        conf.setDesc(appDescConf);

        validator = new AppValidator(conf);
    }

    @Test
    public void shouldBeValidInput() {
        var cmd = new AppCreationRequest("some", "Also desc", null);

        assertDoesNotThrow(() -> validator.validateCreationCmd(cmd));
    }

    @Test
    public void shouldThrow_causeOfTooShortName() {
        var cmd = new AppCreationRequest("sm", "Also desc", null);

        assertThrows(AppValidationException.class, () -> validator.validateCreationCmd(cmd));
    }

    @Test
    public void shouldThrow_causeOfTooLongName() {
        var cmd = new AppCreationRequest("realllllllyyy loooooong", "Also desc", null);

        assertThrows(AppValidationException.class, () -> validator.validateCreationCmd(cmd));
    }

    @Test
    public void shouldThrow_causeOfTooShortDesc() {
        var cmd = new AppCreationRequest("some", "sm", null);

        assertThrows(AppValidationException.class, () -> validator.validateCreationCmd(cmd));
    }

    @Test
    public void shouldThrow_causeOfTooLongDesc() {
        var cmd = new AppCreationRequest("some", "probably toooooo loooong desc", null);

        assertThrows(AppValidationException.class, () -> validator.validateCreationCmd(cmd));
    }

}
