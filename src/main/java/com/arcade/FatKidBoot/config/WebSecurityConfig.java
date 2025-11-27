package com.arcade.FatKidBoot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final UserDetailsService userDetailsService;

    public WebSecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean /*THE FILTER CHAIN*/
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(request ->
                        request.requestMatchers("/users/register", "/users/login").permitAll() /*No Authentication for new ones*/
                                .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable) /*No TOKEN for POST/PUT request*/
                .formLogin(Customizer.withDefaults())  /*Use form to Authorize*/
                .httpBasic(Customizer.withDefaults()); /*Authorize*/
        return http.build();
    }

    //@Bean /*THE USER DETAIL SERVICE*/
    public UserDetailsService /*Returns a UserDetails*/ userDetailsService() {
        UserDetails adminAlireza = User.withUsername("{noop}Alireza").password("9090").roles("ADMIN").build();
        UserDetails John = User.withUsername("John").password("{noop}skyline34").roles("USER").build();

        return new InMemoryUserDetailsManager(adminAlireza, John);
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(14);
    }


    @Bean
    public AuthenticationProvider authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        //provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        provider.setPasswordEncoder(bCryptPasswordEncoder());
        return provider;
    }
}
