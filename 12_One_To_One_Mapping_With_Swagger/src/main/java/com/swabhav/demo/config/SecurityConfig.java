package com.swabhav.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	// security filter chain
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
		httpSecurity.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.GET, "/api/students/**")
						.hasAnyRole("ADMIN", "USER").requestMatchers(HttpMethod.PUT, "/api/students/**")
						.hasRole("ADMIN").requestMatchers(HttpMethod.POST, "/api/students/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/api/students/**").hasRole("ADMIN").anyRequest()
						.authenticated())
				.httpBasic(Customizer.withDefaults())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		return httpSecurity.build();
	}

	@Bean
	UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
		UserDetails admin = User.builder().username("admin").password(passwordEncoder.encode("Admin123")).roles("ADMIN")
				.build();

		UserDetails user = User.builder().username("user").password(passwordEncoder.encode("User123"))
				.roles("USER").build();

		return new InMemoryUserDetailsManager(admin, user);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
