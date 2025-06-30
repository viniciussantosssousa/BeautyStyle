package com.salao.agendamentos.service;

import com.salao.agendamentos.dto.UsuarioDTO;
import com.salao.agendamentos.model.ConfirmationToken;
import com.salao.agendamentos.model.Usuario;
import com.salao.agendamentos.repository.ConfirmationTokenRepository;
import com.salao.agendamentos.repository.UsuarioRepository;
// Atenção aqui: Usaremos a anotação Transactional do Spring para o método de exclusão.
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenRepository tokenRepository;
    private final EmailService emailService;

    // --- LÓGICA DE CADASTRO ---
    public void cadastrar(UsuarioDTO dto) {
        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalStateException("Email já está em uso");
        }

        Usuario usuario = Usuario.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .telefone(dto.getTelefone())
                .senha(passwordEncoder.encode(dto.getSenha()))
                .tipo(dto.getTipo())
                .enabled(false) 
                .build();
        usuarioRepository.save(usuario);

        String tokenValue = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                tokenValue,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15), 
                usuario
        );
        tokenRepository.save(confirmationToken);

        // Em UsuarioService.java
        String link = "https://organic-happiness-vxqg659qg6j2qv5-8080.app.github.dev/api/usuarios/confirm?token=" + tokenValue;
        String emailBody = buildEmail(dto.getNome(), link);
        emailService.send(dto.getEmail(), "Confirme seu cadastro", emailBody);
    }

    // --- LÓGICA DE CONFIRMAÇÃO ---
    // Aqui usamos o Transactional do jakarta pois ele funciona bem com o fluxo do controller
    @jakarta.transaction.Transactional
    public void confirmToken(String tokenValue) {
        System.out.println("--- INICIANDO PROCESSO DE CONFIRMAÇÃO PARA O TOKEN: " + tokenValue + " ---");

        ConfirmationToken token = tokenRepository.findByToken(tokenValue)
                .orElseThrow(() -> {
                    System.out.println(">>> ERRO: Token não foi encontrado no banco de dados.");
                    return new IllegalStateException("Token de confirmação não encontrado.");
                });
        
        System.out.println(">>> LOG: Token encontrado para o usuário: " + token.getUsuario().getEmail());

        if (token.getConfirmedAt() != null) {
            System.out.println(">>> ERRO: O token já foi confirmado anteriormente em: " + token.getConfirmedAt());
            throw new IllegalStateException("Este e-mail já foi confirmado.");
        }

        System.out.println(">>> LOG: Validação de 'já confirmado' passou.");

        if (token.getExpiresAt().isBefore(LocalDateTime.now())) {
            System.out.println(">>> ERRO: O token expirou. Data de expiração: " + token.getExpiresAt());
            throw new IllegalStateException("Seu token de confirmação expirou. Por favor, solicite um novo.");
        }

        System.out.println(">>> LOG: Validação de 'expiração' passou. Preparando para ativar o usuário.");

        token.setConfirmedAt(LocalDateTime.now());
        
        Usuario usuario = token.getUsuario();
        usuario.setEnabled(true);

        tokenRepository.save(token);
        usuarioRepository.save(usuario);
        
        System.out.println(">>> !!! SUCESSO: Usuário " + usuario.getEmail() + " ativado e salvo no banco de dados. !!!");
    }
    
    // --- LÓGICA DE LOGIN ---
    public boolean validarLogin(String email, String senha) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);

        if (usuarioOpt.isEmpty()) {
            return false;
        }

        Usuario usuario = usuarioOpt.get();
        return passwordEncoder.matches(senha, usuario.getSenha()) && usuario.isEnabled();
    }
    
    // --- OUTROS MÉTODOS ---
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @Transactional // Usamos a anotação do Spring para operações de repositório
    public void excluirPorId(Long id) {
        // 1. PRIMEIRO: Deleta os tokens de confirmação que dependem do usuário.
        tokenRepository.deleteAllByUsuarioId(id);
        
        // 2. DEPOIS: Com as dependências removidas, deleta o usuário principal.
        usuarioRepository.deleteById(id);
    }
    
    private String buildEmail(String name, String link) {
        return "<div style='font-family: Arial, sans-serif; font-size: 16px;'>"
                + "<h3>Olá, " + name + "!</h3>"
                + "<p>Obrigado por se cadastrar no nosso salão. Por favor, clique no link abaixo para ativar sua conta:</p>"
                + "<p><a href=\"" + link + "\" style='background-color: #007bff; color: white; padding: 10px 15px; text-decoration: none; border-radius: 5px;'>Ativar Minha Conta</a></p>"
                + "<p>O link expirará em 15 minutos.</p>"
                + "<p>Se você não se cadastrou, por favor, ignore este e-mail.</p>"
                + "</div>";
    }
}
