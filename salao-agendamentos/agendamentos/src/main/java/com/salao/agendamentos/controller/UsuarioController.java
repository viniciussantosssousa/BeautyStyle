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
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // Importe RedirectAttributes

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
    public String cadastrarViaFormulario(@ModelAttribute UsuarioDTO dto, RedirectAttributes ra) { // Mude Model para RedirectAttributes
        dto.setTipo("CLIENTE"); // <--- AQUI: Mudei para "CLIENTE"

        try {
            usuarioService.cadastrar(dto);
            ra.addFlashAttribute("mensagemCadastroSucesso", "Cadastro realizado com sucesso! Faça login para continuar.");
            return "redirect:/login"; // Redireciona para a página de login
        } catch (RuntimeException e) {
            ra.addFlashAttribute("erroCadastro", e.getMessage()); // Adiciona a mensagem de erro
            return "redirect:/cadastro"; // Redireciona de volta para a página de cadastro
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
    public String excluirUsuario(@PathVariable Long id, RedirectAttributes ra) { // Adicione RedirectAttributes
        try {
            usuarioService.excluirPorId(id);
            ra.addFlashAttribute("mensagemExclusao", "Usuário excluído com sucesso.");
        } catch (Exception e) {
            ra.addFlashAttribute("erroExclusao", "Erro ao excluir usuário: " + e.getMessage());
        }
        return "redirect:/api/usuarios/gerenciar";
    }

}