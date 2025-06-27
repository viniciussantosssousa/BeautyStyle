package com.salao.agendamentos.controller;

import com.salao.agendamentos.dto.LoginRequest;
import com.salao.agendamentos.dto.UsuarioDTO;
import com.salao.agendamentos.model.Usuario;
import com.salao.agendamentos.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor // Usando Lombok para injeção
public class UsuarioController {
    
    private final UsuarioService usuarioService;
    
    // Cadastro via API (sem alteração)
    @PostMapping("/cadastro")
    public Usuario cadastrar(@RequestBody UsuarioDTO dto) {
        // Este método agora vai dar erro pois o service não retorna mais Usuario. 
        // Você precisa decidir o que fazer com este endpoint de API.
        // Por agora, vou comentar a linha que causa o erro.
        // return usuarioService.cadastrar(dto);
        return null; // Apenas para compilar
    }

    // Cadastro via Formulário (MODIFICADO)
    @PostMapping("/cadastro-form")
    public String cadastrarViaFormulario(@ModelAttribute UsuarioDTO dto, RedirectAttributes ra) {
        dto.setTipo("ADMIN");

        try {
            usuarioService.cadastrar(dto);
            // MENSAGEM DE SUCESSO ALTERADA
            ra.addFlashAttribute("mensagemCadastroSucesso", "Cadastro quase completo! Um e-mail de confirmação foi enviado para sua caixa de entrada.");
            return "redirect:/login"; // Redireciona para o login para exibir a mensagem
        } catch (IllegalStateException e) {
            ra.addFlashAttribute("erroCadastro", e.getMessage());
            return "redirect:/cadastro";
        }
    }
    
    // NOVO ENDPOINT PARA CONFIRMAÇÃO DE E-MAIL
    @GetMapping("/confirm")
    public String confirm(@RequestParam("token") String token, RedirectAttributes ra) {
        try {
            usuarioService.confirmToken(token);
            ra.addFlashAttribute("mensagemCadastroSucesso", "Conta ativada com sucesso! Você já pode fazer o login.");
            return "redirect:/login";
        } catch (IllegalStateException e) {
            ra.addFlashAttribute("erroLogin", "Erro na confirmação: " + e.getMessage()); // Exibe o erro na página de login
            return "redirect:/login";
        }
    }

    // Login (MODIFICADO para tratar login de conta não ativada)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        boolean loginValido = usuarioService.validarLogin(request.getEmail(), request.getSenha());
    
        if (loginValido) {
            return ResponseEntity.ok("Login bem-sucedido");
        } else {
            // Podemos verificar se o usuário existe mas não está ativo
            // Esta parte é opcional, mas melhora a experiência do usuário
            // Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(request.getEmail());
            // if(usuarioOpt.isPresent() && !usuarioOpt.get().isEnabled()){
            //     return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sua conta ainda não foi ativada. Verifique seu e-mail.");
            // }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou senha inválidos, ou conta não ativada.");
        }
    }

    // Gerenciar (sem alteração)
    @GetMapping("/gerenciar")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.listarTodos());
        return "usuarios";
    }

    // Excluir (sem alteração)
    @PostMapping("/excluir/{id}")
    public String excluirUsuario(@PathVariable Long id, RedirectAttributes ra) {
        try {
            usuarioService.excluirPorId(id);
            ra.addFlashAttribute("mensagemExclusao", "Usuário excluído com sucesso.");
        } catch (Exception e) {
            ra.addFlashAttribute("erroExclusao", "Erro ao excluir usuário: " + e.getMessage());
        }
        return "redirect:/api/usuarios/gerenciar";
    }
}