package com.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SecurityConfig  {

    @Bean
    UserDetailsService userDetailsService(PasswordEncoder passwordEncoder){

        List<UserDetails> userList= new ArrayList<>();

        User user1= new User("mike", passwordEncoder.encode("password"),
                List.of(new SimpleGrantedAuthority("ROLE_Admin")));
        User user2= new User("ozzy", passwordEncoder.encode("password"),
                List.of(new SimpleGrantedAuthority("ROLE_Manager")));
        userList.add(user1); userList.add(user2);

        return new InMemoryUserDetailsManager(userList);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{

        return httpSecurity
                .authorizeHttpRequests(authorize-> authorize
                        .requestMatchers("/","/login","/fragments/**","/assets/**","/images/**").permitAll()
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .build();


    }
}
