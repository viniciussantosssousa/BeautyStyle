package com.salao.agendamentos.service;

import com.salao.agendamentos.dto.UsuarioDTO;
import com.salao.agendamentos.model.Usuario;
import com.salao.agendamentos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario cadastrar(UsuarioDTO dto) {
        Usuario usuario = Usuario.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .telefone(dto.getTelefone())
                .senha(dto.getSenha()) // depois vamos criptografar
                .tipo(dto.getTipo())
                .build();
        return usuarioRepository.save(usuario);
    }

    // ✅ Adicione este método abaixo:
    public boolean validarLogin(String email, String senha) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        return usuarioOpt.isPresent() && usuarioOpt.get().getSenha().equals(senha);
    }
}
