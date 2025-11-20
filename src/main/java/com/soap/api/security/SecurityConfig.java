// com.soap.api.security.SecurityConfig.java
package com.soap.api.security;

import com.soap.api.dto.Role;
import com.soap.api.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Arrays;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final AuthEntryPoint entryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationFilter jwtFilter = new AuthenticationFilter(jwtUtil);

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(ex -> ex.authenticationEntryPoint(entryPoint))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/user/register", "/api/user/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/posts", "/api/posts/byUser", "/api/posts/search").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/posts").hasAnyRole(Arrays.stream(Role.values()).map(Enum::name).toArray(String[]::new))
                        .requestMatchers(HttpMethod.PUT, "/api/posts").hasAnyRole(Arrays.stream(Role.values()).map(Enum::name).toArray(String[]::new))
                        .requestMatchers(HttpMethod.DELETE, "/api/posts").hasAnyRole(Arrays.stream(Role.values()).map(Enum::name).toArray(String[]::new))//Arrays.toString(Role.values())
                        .requestMatchers(HttpMethod.GET, "/api/users").hasRole(Role.ADMIN.toString())
                        .requestMatchers(HttpMethod.DELETE, "/api/user").hasRole(Role.ADMIN.toString())
                        .requestMatchers(HttpMethod.GET, "/api/user").hasRole(Role.ADMIN.toString())
                        .requestMatchers(HttpMethod.PUT, "/api/user").hasAnyRole(Arrays.stream(Role.values()).map(Enum::name).toArray(String[]::new))
                        //.requestMatchers("/error").permitAll()
                        .anyRequest().authenticated()
                );

        http.addFilterBefore(jwtFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
