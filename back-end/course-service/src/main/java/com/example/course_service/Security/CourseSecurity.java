package com.example.course_service.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class CourseSecurity{

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      return   http
                .csrf(csrf->csrf.disable())
                .authorizeHttpRequests(authorizeRequest ->authorizeRequest
                        .requestMatchers(HttpMethod.POST,"courses/course/name").permitAll()
                        // Admins can access /admin paths (create, delete courses)
                        .requestMatchers("courses/admin/**").hasAuthority("ADMIN")
                // Regular users can access the course viewing paths
                .requestMatchers("/course/").hasAnyAuthority("USER", "ADMIN")
                        .anyRequest()
                        .authenticated())
              .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Empty or dummy user service - if you do not want login in this service
        return username -> {
            throw new UsernameNotFoundException("User not found: " + username);
        };

    }

}
