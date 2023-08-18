package com.dixmillescodeurs.poo.hello.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.DefaultSecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    private final LoginSuccessHandler loginSuccessHandler;

    public SecurityConfiguration(LoginSuccessHandler loginSuccessHandler) {
        this.loginSuccessHandler = loginSuccessHandler;
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user = User.withUsername("user")
                .password(passwordEncoder.encode("password"))
                .roles("USER")
                .build();

        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder.encode("admin"))
                .roles("USER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public DefaultSecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> {
                            authz.requestMatchers("/").permitAll()
                                    .requestMatchers("/voyages").permitAll()
                                    .requestMatchers("/api/voyage/**").permitAll()
                                    .requestMatchers("/v3/api-docs/**", "/swagger-ui/**",
                                            "/swagger-ui.html",
                                            "/v2/api-docs/**",
                                            "/swagger-resources/**","/api/**").permitAll()
                                    .anyRequest().authenticated();
                        }
                )
                .formLogin(login ->login.successHandler(loginSuccessHandler) );
        return http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable()).build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return encoder;
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/assets/**", "/fonts/**", "/img/**", "/resources/**", "/static/**", "/css/**", "/js/**", "/vendor/**");
    }
}
