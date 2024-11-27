package com.security.config;

import com.security.service.SecurityService;
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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SecurityConfig  {

    private final SecurityService securityService;

    public SecurityConfig(SecurityService securityService) {
        this.securityService = securityService;
    }

//    @Bean
//    UserDetailsService userDetailsService(PasswordEncoder passwordEncoder){
//
//        List<UserDetails> userList= new ArrayList<>();
//
//        User user1= new User("mike", passwordEncoder.encode("password"),
//                List.of(new SimpleGrantedAuthority("ROLE_Admin")));
//        User user2= new User("ozzy", passwordEncoder.encode("password"),
//                List.of(new SimpleGrantedAuthority("ROLE_Manager")));
//        userList.add(user1); userList.add(user2);
//
//        return new InMemoryUserDetailsManager(userList);
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{

        return httpSecurity
                .authorizeHttpRequests(authorize-> authorize
                        .requestMatchers("/user/**").hasAuthority("Admin")
                        .requestMatchers("/project/**").hasAuthority("Manager")
                        .requestMatchers("/task/**").hasAuthority("Manager")
                        .requestMatchers("/task/employee/**").hasAuthority("Employee")
                        //.requestMatchers("/task/employee/**").hasAuthority("Employee","Admin","Manager")
                        .requestMatchers(
                                "/",
                                "/login",
                                "/fragments/**",
                                "/assets/**",
                                "/images/**"
                        ).permitAll()
                        .anyRequest().authenticated())
              //  .httpBasic(Customizer.withDefaults())
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/welcome")
                        .failureUrl("/login?error=true")
                        .permitAll())
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login"))
                .rememberMe(remember -> remember
                        .tokenValiditySeconds(120)
                        .key("security")
                        .userDetailsService(securityService))

                .build();
    }
}
