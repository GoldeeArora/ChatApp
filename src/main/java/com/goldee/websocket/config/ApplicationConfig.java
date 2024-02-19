package com.goldee.websocket.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.goldee.websocket.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private PasswordEncoder encoder;

	@SuppressWarnings("null")
	@Bean
	public UserDetailsService userDetailsService() {
		return username -> userRepo.findById(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService());
		;
		daoAuthenticationProvider.setPasswordEncoder(encoder);
		return daoAuthenticationProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

}
