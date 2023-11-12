package com.cenfotec.adaka.app.config;


import com.cenfotec.adaka.app.config.auth.filters.JwtAuthenticationFilter;
import com.cenfotec.adaka.app.config.auth.filters.JwtValidationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Properties;

@Configuration
public class SpringSecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests()
                .antMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/users/{email}").hasAnyRole("ADMIN", "NURSE","MEDICAL_DOCTOR")
                .antMatchers(HttpMethod.GET, "/users/{id}").hasAnyRole("ADMIN", "NURSE","MEDICAL_DOCTOR")
                .antMatchers(HttpMethod.POST, "/users/{id}/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/users/{id}").hasRole("ADMIN")

                .antMatchers(HttpMethod.POST, "/device").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/devices").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/devices/{id}").hasAnyRole("ADMIN","NURSE","MEDICAL_DOCTOR")
                .antMatchers(HttpMethod.DELETE, "/devices/{id}").hasRole("ADMIN")

                .antMatchers(HttpMethod.POST, "/medical").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/medical/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/medical").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/medical/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/medical/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/medical/room/{id}").hasAnyRole("ADMIN","NURSE","MEDICAL_DOCTOR")
                .antMatchers(HttpMethod.GET, "/medical/room/").hasAnyRole("ADMIN","NURSE","MEDICAL_DOCTOR")


                .antMatchers(HttpMethod.POST, "/subscription/**").permitAll()
                .antMatchers(HttpMethod.POST, "/lecture/{id}").permitAll()
                .antMatchers(HttpMethod.POST, "/user/recover").permitAll()

                .anyRequest().authenticated()
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationConfiguration.getAuthenticationManager()))
                .addFilter(new JwtValidationFilter(authenticationConfiguration.getAuthenticationManager()))
                .csrf(config -> config.disable())
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .build();

    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
        corsConfiguration.setAllowedMethods(Arrays.asList("*"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }

    @Bean
    FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(
                new CorsFilter(corsConfigurationSource()));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }


}
