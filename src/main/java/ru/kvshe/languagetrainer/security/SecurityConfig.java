package ru.kvshe.languagetrainer.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)


                .authorizeHttpRequests(request -> {
//                    request.requestMatchers("/", "/style.css/**", "/js/**", "/images/**").permitAll(); // разрешаем доступ к статическим ресурсам
                    request.requestMatchers("/", "/static/**").permitAll(); // разрешаем доступ к статическим ресурсам
                    request.anyRequest().permitAll();
                })

                // todo
                .build();
    }
}
