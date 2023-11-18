package com.cenfotec.adaka.app.config;


import com.cenfotec.adaka.app.config.auth.filters.JwtAuthenticationFilter;
import com.cenfotec.adaka.app.config.auth.filters.JwtValidationFilter;
import com.cenfotec.adaka.app.domain.Role;
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
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
@EnableWebSecurity
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
    //"/add/device/{roomId}/{deviceId}"

    @Bean
     SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //se permite a todos.. y despues del autheticated todos requiren autenticacion

        http

                .authorizeHttpRequests()
                        //authorized -> authorized
                        //USERS
                                .antMatchers(HttpMethod.POST, "/user/recover").permitAll()
                                .antMatchers(HttpMethod.GET, "/user/email/{email}").hasAnyRole("ADMIN","NURSE", "MEDICAL_DOCTOR")
                                .antMatchers(HttpMethod.GET, "/user/all/{managerId}").hasRole("ADMIN")
                                .antMatchers(HttpMethod.GET, "/user/{id}").hasRole("ADMIN")
                                .antMatchers(HttpMethod.POST, "/user/{parentId}/{medicalCenterId}").hasRole("ADMIN")
                                .antMatchers(HttpMethod.PUT, "/user/{id}/update").hasRole("ADMIN")
                                .antMatchers(HttpMethod.PUT, "/user/{id}/updateUser").hasAnyRole("ADMIN","NURSE", "MEDICAL_DOCTOR")
                                .antMatchers(HttpMethod.PUT, "/user/password/{id}").hasAnyRole("ADMIN","NURSE", "MEDICAL_DOCTOR")
                                .antMatchers(HttpMethod.DELETE, "/user/{id}").hasRole("ADMIN")

                        //BACKOFFICE
                                .antMatchers(HttpMethod.POST, "/subscription/save/admin").permitAll()
                                .antMatchers(HttpMethod.POST,"/subscription/").hasRole("ADMIN")
                                .antMatchers(HttpMethod.GET,"/subscription/{id}").hasRole("ADMIN")

                        //MEDICAL CENTER
                                .antMatchers(HttpMethod.GET,"/medical/all").permitAll()
                                .antMatchers(HttpMethod.GET,"/medical/all/{id}").hasAnyRole("ADMIN","NURSE", "MEDICAL_DOCTOR")
                                .antMatchers(HttpMethod.GET,"/medical/{id}").hasAnyRole("ADMIN","NURSE", "MEDICAL_DOCTOR")
                                .antMatchers(HttpMethod.POST, "/medical/{id}").hasRole("ADMIN")
                                .antMatchers(HttpMethod.PUT, "/medical/changeMedicalStatus/{id}/{status}").permitAll() //Revisar
                                .antMatchers(HttpMethod.PUT, "/medical/changeMedical/{id}").hasRole("ADMIN")
                                .antMatchers(HttpMethod.DELETE, "/medical/delete/{id}").hasRole("ADMIN")


                        //ROOM
                                .antMatchers(HttpMethod.GET,"/room/all/{id}").hasAnyRole("ADMIN","NURSE", "MEDICAL_DOCTOR")
                                .antMatchers(HttpMethod.GET,"/room/allUser/{id}").hasAnyRole("ADMIN","NURSE", "MEDICAL_DOCTOR")
                                .antMatchers(HttpMethod.GET,"/room/{id}").hasAnyRole("ADMIN","NURSE", "MEDICAL_DOCTOR")
                                .antMatchers(HttpMethod.POST,"/room/{id}").hasRole("ADMIN")
                                .antMatchers(HttpMethod.PUT,"/room/changeRoom/{roomId}/{medicalCenterId}").hasRole("ADMIN")
                                .antMatchers(HttpMethod.DELETE, "/room/delete/{id}").hasRole("ADMIN")
                                .antMatchers(HttpMethod.PUT,"/room/add/device/{roomId}/{deviceId}").permitAll()//ADMIN ONLY NOT WORKING
                                .antMatchers(HttpMethod.PUT,"/room/changeRoomDevice/{roomId}").permitAll()



                        //DEVICE
                                .antMatchers(HttpMethod.GET,"/devices/all/{id}").hasAnyRole("ADMIN","NURSE", "MEDICAL_DOCTOR")
                                .antMatchers(HttpMethod.GET,"/devices/{id}").hasAnyRole("ADMIN","NURSE", "MEDICAL_DOCTOR")
                                .antMatchers(HttpMethod.POST,"/devices/{adminId}/save").hasRole("ADMIN")
                                .antMatchers(HttpMethod.DELETE, "/devices/delete/{id}").hasRole("ADMIN")
                        //METRICS
                                .antMatchers("/metrics/**").permitAll()

                                .anyRequest().authenticated()
                                .and()

                .addFilter(new JwtAuthenticationFilter(authenticationConfiguration.getAuthenticationManager()))
                .addFilter(new JwtValidationFilter(authenticationConfiguration.getAuthenticationManager()))
                .csrf().disable()
                .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));


        return http.build();

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
