package daar.projects.escv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@EnableElasticsearchRepositories(basePackages = "daar.projects.escv.repository")
public class EscvApplication {

	public static void main(String[] args) {
		SpringApplication.run(EscvApplication.class, args);
	}

}
