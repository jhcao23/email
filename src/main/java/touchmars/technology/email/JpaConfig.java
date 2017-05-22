package touchmars.technology.email;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories
@EnableTransactionManagement
public class JpaConfig {
	
	@Bean
	public ReportProperties reportProperties(){
		return new ReportProperties();
	}

}
