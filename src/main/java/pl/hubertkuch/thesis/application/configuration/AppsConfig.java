package pl.hubertkuch.thesis.application.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@ConfigurationProperties("app")
@PropertySource("classpath:/config.applications.properties")
public class AppsConfig {
    private AppNameConfiguration name;
    private AppDescConfiguration desc;
}
