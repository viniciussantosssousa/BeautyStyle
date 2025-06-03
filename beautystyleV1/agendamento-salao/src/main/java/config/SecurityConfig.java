package com.salao.agendamento.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher; // Importe esta classe

@Configuration // Indica que esta classe contém configurações do Spring
@EnableWebSecurity // Habilita a segurança web do Spring Security
public class SecurityConfig {

    @Bean
    // Configura a cadeia de filtros de segurança HTTP
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                // Páginas públicas (qualquer um pode acessar)
                .requestMatchers("/", "/home", "/login", "/register", "/css/**", "/js/**", "/images/**").permitAll()
                // Páginas que exigem perfil de ADMIN
                .requestMatchers("/admin/**").hasRole("ADMIN")
                // Todas as outras requisições exigem autenticação
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login") // Define a URL da sua página de login customizada
                .defaultSuccessUrl("/dashboard", true) // Redireciona para /dashboard após login bem-sucedido
                .permitAll() // Permite acesso a todos ao formulário de login
            )
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // URL para logout
                .logoutSuccessUrl("/login?logout") // Redireciona para /login com param logout após logout
                .permitAll()
            );
        return http.build();
    }

    @Bean
    // Define o codificador de senhas (BCrypt para segurança)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}