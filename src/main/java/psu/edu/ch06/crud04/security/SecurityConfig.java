package psu.edu.ch06.crud04.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpsec) throws Exception {
		return httpsec
				// CSRF is not needed: the API is stateless and uses basic auth
				.csrf(customizer -> customizer.disable())
				// every endpoint requires an authenticated user
				.authorizeHttpRequests(requests -> requests.anyRequest().authenticated())
				// HTTP basic authentication (Authorization: Basic base64(user:password))
				.httpBasic(Customizer.withDefaults())
				// no HTTP session: credentials are sent with every request
				.sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.build();
	}

	// Users come from the database through MyUserDetailsService.
	// Their passwords are stored as BCrypt hashes (see data.sql).
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}
}
