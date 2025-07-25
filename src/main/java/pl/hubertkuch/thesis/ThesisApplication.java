package pl.hubertkuch.thesis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class ThesisApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThesisApplication.class, args);
	}

}
