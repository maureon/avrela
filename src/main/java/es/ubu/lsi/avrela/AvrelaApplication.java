package es.ubu.lsi.avrela;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class AvrelaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AvrelaApplication.class, args);
		if (System.getenv("GITHUB_TOKEN") != null){
			log.info("Using {} for GitHub authentication", System.getenv("GITHUB_TOKEN"));
		}else{
			log.warn("No GITHUB_TOKEN environment value detected, GitHub API rate limits will apply. See https://docs.github.com/en/rest/overview/resources-in-the-rest-api?apiVersion=2022-11-28#rate-limiting");
		}
	}

}
