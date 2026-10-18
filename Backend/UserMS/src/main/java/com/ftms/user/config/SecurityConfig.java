
package com.ftms.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))


.authorizeHttpRequests(auth -> auth
    .requestMatchers("/user/register", "/user/login").permitAll()
    .requestMatchers(
        org.springframework.http.HttpMethod.GET, "/user/*"
    ).permitAll()
    .requestMatchers(
        org.springframework.http.HttpMethod.GET, "/user/*/roles"
    ).permitAll()
    .anyRequest().authenticated()
);


		return http.build();
	}
}
