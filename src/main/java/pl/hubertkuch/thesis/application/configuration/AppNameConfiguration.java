package pl.hubertkuch.thesis.application.configuration;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.hubertkuch.thesis.util.configuration.HasLength;

@Data
@EqualsAndHashCode(callSuper = true)
public class AppNameConfiguration extends HasLength {
}
