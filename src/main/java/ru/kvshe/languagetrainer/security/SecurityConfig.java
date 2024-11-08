package ru.kvshe.languagetrainer.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Конфигурация безопасности веб-приложения.
 * Обеспечивает настройку безопасности через Spring Security.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    /**
     * Бин для кодирования паролей с использованием алгоритма BCrypt.
     *
     * @return экземпляр BCryptPasswordEncoder для кодирования паролей
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Настройка фильтров безопасности для приложения.
     * Определяет доступ к различным ресурсам и конфигурацию форм входа и выхода.
     *
     * @param http объект конфигурации безопасности
     * @return настроенный SecurityFilterChain
     * @throws Exception если происходит ошибка в конфигурации безопасности
     */
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

                .formLogin(request -> {
                    request.loginPage("/login");
                    request.failureUrl("/login?error");
                    request.defaultSuccessUrl("/");
                    request.permitAll();
                })

                .logout(request -> {
                    request.logoutSuccessUrl("/"); // url для перенаправления после успешного выхода
                })

                .build();
    }
}
