package com.goldee.websocket.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.*;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
        @Autowired
        private AuthenticationProvider authenticationProvider;
        @Autowired
        private JWTAuthenticationFilter jwtAuthenticationFilter;

        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                System.out.println("cors has been updated");
                http
                                // .cors(httpSecurityCorsConfigurer ->
                                // httpSecurityCorsConfigurer.configurationSource(
                                // request -> new CorsConfiguration().applyPermitDefaultValues()))
                                // .cors(withDefaults())
                                .csrf(csrf -> csrf.disable())
                                .cors(c -> c.configurationSource(corsConfigurationSource()))
                                .authorizeHttpRequests((authorize) -> authorize
                                                .requestMatchers("/", "/index.html", "/static/**", "/api/auth/**",
                                                                "/**.jpg",
                                                                "/ws/**")
                                                .permitAll()
                                                // .requestMatchers("/**").permitAll()
                                                .anyRequest().authenticated())
                                .sessionManagement((session) -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(jwtAuthenticationFilter,
                                                UsernamePasswordAuthenticationFilter.class);
                System.out.println(http + " I have disabled the cors");
                // http
                // .csrf(csrf -> csrf.disable())
                // .authorizeHttpRequests((authorize) -> authorize
                // .requestMatchers("/**").permitAll().anyRequest().authenticated());
                // http
                // .csrf()
                // .disable()
                // .authorizeHttpRequests()
                // .requestMatchers("/api/auth/**")
                // .permitAll()
                // .anyRequest()
                // .authenticated()
                // .and()
                // .sessionManagement()
                // .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // .and()
                // .authenticationProvider(authenticationProvider)
                // .addFilterBefore(jwtAuthenticationFilter,
                // UsernamePasswordAuthenticationFilter.class);
                return http.build();
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();
                // configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000/"));
                // configuration.setAllowedMethods(
                // Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE",
                // "CONNECT"));
                // configuration.setAllowedHeaders(Arrays.asList("Content-Type",
                // "Authorization"));
                // configuration.addAllowedOrigin("*"); // Allow all origins
                // configuration.addAllowedMethod("*"); // Allow all HTTP methods
                // configuration.addAllowedHeader("*"); // Allow all header
                configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000",
                                "http://127.0.0.1:5501/,http://localhost:60207"));
                configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
                configuration.setAllowCredentials(true);
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                return source;
        }

}
