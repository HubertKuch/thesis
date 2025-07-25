package pl.hubertkuch.thesis.application.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@ConfigurationProperties("app")
@PropertySource("classpath:/applications.properties")
public class ApplicationConfiguration {
    private ApplicationNameConfiguration name;
    private ApplicationDescConfiguration desc;
}
