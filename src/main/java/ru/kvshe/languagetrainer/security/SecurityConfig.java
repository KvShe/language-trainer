package ru.kvshe.languagetrainer.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
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
                    request.requestMatchers(
                            "/css/**",
                            "/js/**",
                            "/img/**",
                            "/sound/**",

                            "/",
                            "/register",
                            "/login"
                    ).permitAll();

                    request.anyRequest().authenticated();
                })

                .formLogin(Customizer.withDefaults())

//                .formLogin(request -> {
//                    request.loginPage("/login");
//                    request.failureUrl("/login?error");
//                    request.defaultSuccessUrl("/");
//                    request.permitAll();
//                })

//                .logout(request -> {
//                    request.logoutUrl("/logout"); // url для запроса выхода
//                    request.logoutSuccessUrl("/"); // url для перенаправления после успешного выхода
//                })

                .build();
    }
}
