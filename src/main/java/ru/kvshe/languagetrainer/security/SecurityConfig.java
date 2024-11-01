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
import ru.kvshe.languagetrainer.model.Role;

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
                            "/sound/**"
                    ).permitAll(); // разрешаем доступ к статическим ресурсам
                    request.requestMatchers(
                            "/",
                            "/register"
//                            "/login"
                    ).permitAll();
//                    request.anyRequest().permitAll();
                    request.anyRequest().hasAnyAuthority(Role.USER.getName(), Role.ADMIN.getName());
                })

                .formLogin(Customizer.withDefaults())

                // todo
                .build();
    }
}
