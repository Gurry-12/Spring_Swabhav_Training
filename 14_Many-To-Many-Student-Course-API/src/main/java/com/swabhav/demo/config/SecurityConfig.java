package com.swabhav.demo.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationProvider authenticationProvider)
			throws Exception {

		http
				.cors(Customizer.withDefaults())
				.csrf(AbstractHttpConfigurer::disable)
				.authenticationProvider(authenticationProvider)
				.authorizeHttpRequests(
						auth -> auth.requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**")
								.permitAll().requestMatchers(HttpMethod.GET, "/api/students/**")
								.hasAnyRole("USER", "ADMIN").requestMatchers(HttpMethod.GET, "/api/courses/**")
								.hasAnyRole("USER", "ADMIN").requestMatchers(HttpMethod.POST, "/api/students/**")
								.hasRole("ADMIN").requestMatchers(HttpMethod.PUT, "/api/students/**").hasRole("ADMIN")
								.requestMatchers(HttpMethod.DELETE, "/api/students/**").hasRole("ADMIN")
								.requestMatchers(HttpMethod.POST, "/api/courses/**").hasRole("ADMIN")
								.requestMatchers(HttpMethod.PUT, "/api/courses/**").hasRole("ADMIN")
								.requestMatchers(HttpMethod.DELETE, "/api/courses/**").hasRole("ADMIN").anyRequest()
								.authenticated())
				.httpBasic(Customizer.withDefaults())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		return http.build();
	}

	@Bean
	AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService,
			PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder);
		return authenticationProvider;
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {

	    CorsConfiguration configuration = new CorsConfiguration();

	    configuration.setAllowedOrigins(
	            List.of("http://localhost:5173")
	    );

	    configuration.setAllowedMethods(
	            List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")
	    );

	    configuration.setAllowedHeaders(
	            List.of("*")
	    );

	    configuration.setAllowCredentials(true);

	    UrlBasedCorsConfigurationSource source =
	            new UrlBasedCorsConfigurationSource();

	    source.registerCorsConfiguration("/**", configuration);

	    return source;
	}
}
