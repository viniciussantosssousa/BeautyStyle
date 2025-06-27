package com.salao.agendamentos.config;

import com.salao.agendamentos.security.UsuarioDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private UsuarioDetailsService usuarioDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Lista de URLs públicas
                .requestMatchers(
                    "/", 
                    "/home", 
                    "/login", 
                    "/api/usuarios/login", 
                    "/cadastro", 
                    "/api/usuarios/cadastro-form", 
                    "/css/**", 
                    "/img/**", 
                    "/js/**",
                    // ==============================================================================
                    // ============== CORREÇÃO FINAL ADICIONADA AQUI ================================
                    // Adicionando o endpoint de confirmação à lista de permissões públicas
                    "/api/usuarios/confirm/**" 
                    // ==============================================================================
                ).permitAll()
                // Lista de URLs restritas ao ADMIN
                .requestMatchers("/agendar", "/agendamentos", "/cancelar/**", "/editar/**").hasRole("ADMIN") 
                .requestMatchers("/api/usuarios/gerenciar", "/api/usuarios/excluir/**").hasRole("ADMIN")
                // Qualquer outra requisição precisa de autenticação
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/home", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(usuarioDetailsService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }
}
