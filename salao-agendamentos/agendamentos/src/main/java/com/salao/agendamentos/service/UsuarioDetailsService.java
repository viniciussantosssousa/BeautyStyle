package com.salao.agendamentos.security;

import com.salao.agendamentos.model.Usuario;
import com.salao.agendamentos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException; // <-- IMPORTANTE: Adicionar esta importação
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário ou senha inválidos"));

        // ==============================================================================
        // ======================= CORREÇÃO ADICIONADA AQUI =============================
        // ==============================================================================
        // Verifica se a conta do usuário foi ativada via e-mail
        if (!usuario.isEnabled()) {
            throw new DisabledException("Conta inativa. Por favor, confirme seu e-mail para poder fazer o login.");
        }
        // ==============================================================================
        
        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getSenha())
                .roles(usuario.getTipo())
                .build();
    }
}