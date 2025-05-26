package com.Airline.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.Airline.Filters.JwtAuthFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private JwtAuthFilter authFilter;
	@Autowired
	private UserDetailsService userDetails;
	
	@Bean
	 SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		return http
				.csrf(csrf->csrf.disable())
				.authorizeHttpRequests(request->request
				.requestMatchers("/api/auth/register", "/api/auth/login").permitAll()
				.requestMatchers("/api/flights/**").hasRole("ADMIN")
				.requestMatchers("/api/bookings/**").hasRole("USER")
				.anyRequest().authenticated())
				.authenticationProvider(authProvider()) // Registered our custom provider use it  instead of default
				.sessionManagement(session ->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}
	AuthenticationProvider authProvider() {
		DaoAuthenticationProvider provider  = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetails);  //It tell spring where to load users from 
		provider.setPasswordEncoder(passwordEncoder()); //It tells spring how to verify passwords
		return provider;
	}
	@Bean
	 PasswordEncoder passwordEncoder() {
	 return new BCryptPasswordEncoder();
	}
	@Bean
	 AuthenticationManager autheManager(AuthenticationConfiguration auth) throws Exception {
		return auth.getAuthenticationManager();
	}
}