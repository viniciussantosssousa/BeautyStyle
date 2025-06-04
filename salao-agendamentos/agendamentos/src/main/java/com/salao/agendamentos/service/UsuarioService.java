package com.salao.agendamentos.service;

import com.salao.agendamentos.dto.UsuarioDTO;
import com.salao.agendamentos.model.Usuario;
import com.salao.agendamentos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; // Adicione esta importação
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Injetar PasswordEncoder aqui

    public Usuario cadastrar(UsuarioDTO dto) {
        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email já está em uso");
        }

        if (usuarioRepository.findByTelefone(dto.getTelefone()).isPresent()) {
            throw new RuntimeException("Telefone já está em uso");
        }

        Usuario usuario = Usuario.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .telefone(dto.getTelefone())
                .senha(passwordEncoder.encode(dto.getSenha())) // Codifica a senha antes de salvar
                .tipo(dto.getTipo())
                .build();

        return usuarioRepository.save(usuario);
    }

    public boolean validarLogin(String email, String senha) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        // Compara a senha fornecida (texto puro) com a senha codificada no banco de dados
        return usuarioOpt.isPresent() && passwordEncoder.matches(senha, usuarioOpt.get().getSenha());
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public void excluirPorId(Long id) {
        usuarioRepository.deleteById(id);
    }

}