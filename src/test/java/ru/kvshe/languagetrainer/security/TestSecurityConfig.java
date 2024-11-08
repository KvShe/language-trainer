package ru.kvshe.languagetrainer.security;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

//@TestConfiguration
//@EnableWebSecurity  // Добавляем эту аннотацию для правильной инициализации HttpSecurity
//@Profile("test")
//public class TestSecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests().anyRequest().permitAll();  // Разрешаем все запросы
////                .and()
////                .csrf().disable();  // Отключаем CSRF для тестов
//        return http.build();
//    }
//}
