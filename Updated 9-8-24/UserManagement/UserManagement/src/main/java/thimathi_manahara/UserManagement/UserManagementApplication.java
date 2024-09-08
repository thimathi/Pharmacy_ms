package thimathi_manahara.UserManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootApplication
public class UserManagementApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(UserManagementApplication.class, args);
		Environment env = applicationContext.getEnvironment();
		String port = env.getProperty("server.port");
		System.out.println("Running User Stack with Port: " + port);
	}

	// CORS Configuration
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
 						.allowedOriginPatterns("*")
						//.allowedOrigins("http://localhost:3039")
						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
						.allowedHeaders("*")
						.allowCredentials(true);
			}
		};
	}

	// Security Configuration
	@EnableWebSecurity
	public class SecurityConfig {

		@Bean
		public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
			http.cors()  // Enable CORS
					.and()
					.csrf().disable();  // Optionally disable CSRF if you don't need it
			return http.build();
		}
	}
}
