package com.salao.agendamentos.controller;

import com.salao.agendamentos.dto.LoginRequest;
import com.salao.agendamentos.dto.UsuarioDTO;
import com.salao.agendamentos.model.Usuario;
import com.salao.agendamentos.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/cadastro")
    public Usuario cadastrar(@RequestBody UsuarioDTO dto) {
        return usuarioService.cadastrar(dto);
    }

    @PostMapping("/cadastro-form")
    public String cadastrarViaFormulario(@ModelAttribute UsuarioDTO dto, Model model) {
        dto.setTipo("ADMIN");

        try {
            usuarioService.cadastrar(dto);
            return "redirect:/agendamentos";
        } catch (RuntimeException e) {
            model.addAttribute("erro", e.getMessage());
            return "cadastro";
        }
        
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

    @GetMapping("/gerenciar")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.listarTodos());
        return "usuarios";
    }

    @PostMapping("/excluir/{id}")
    public String excluirUsuario(@PathVariable Long id) {
        usuarioService.excluirPorId(id);
        return "redirect:/api/usuarios/gerenciar";
    }

}
