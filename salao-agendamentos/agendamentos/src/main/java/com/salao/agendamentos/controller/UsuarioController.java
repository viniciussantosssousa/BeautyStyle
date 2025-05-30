package com.salao.agendamentos.controller;

import com.salao.agendamentos.dto.LoginRequest;
import com.salao.agendamentos.dto.UsuarioDTO;
import com.salao.agendamentos.model.Usuario;
import com.salao.agendamentos.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/cadastro")
    public Usuario cadastrar(@RequestBody UsuarioDTO dto) {
        return usuarioService.cadastrar(dto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        boolean loginValido = usuarioService.validarLogin(request.getEmail(), request.getSenha());

        if (loginValido) {
            return ResponseEntity.ok("Login bem-sucedido");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou senha inválidos");
        }
    }
}
