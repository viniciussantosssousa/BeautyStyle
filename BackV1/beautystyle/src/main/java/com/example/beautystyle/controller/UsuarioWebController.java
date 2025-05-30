package com.example.beautystyle.controller;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.beautystyle.model.Usuario;
import com.example.beautystyle.service.UsuarioService;

import org.springframework.http.HttpStatus;

@Controller
@RequestMapping("/usuarios")
public class UsuarioWebController {

    private final UsuarioService usuarioService;

    public UsuarioWebController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Mapeia GET /usuarios → redireciona para /usuarios/listar
    @GetMapping
    public String index() {
        return "redirect:/usuarios/listar";
    }

    // 1. Página de cadastro
    @GetMapping("/cadastrar")
    public String exibirFormCadastro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuarios/form";
    }

    @PostMapping("/cadastrar")
    public String cadastrarUsuario(
            @Valid @ModelAttribute("usuario") Usuario usuario,
            BindingResult result,
            RedirectAttributes ra) {

        if (result.hasErrors()) {
            // repopula o objeto no formulário em caso de erro
            return "usuarios/form";
        }
        usuarioService.salvarUsuario(usuario);
        ra.addFlashAttribute("success", "Usuario cadastrada com sucesso!");
        return "redirect:/usuarios/listar";
    }

    // 2. Página de listagem
    @GetMapping("/listar")
    public String listarUsuarios(Model model) {
        model.addAttribute("lista", usuarioService.listarUsuarios());
        return "usuarios/lista";
    }

    // 3. Detalhes e exclusão
    @GetMapping("/{id}")
    public String detalhesUsuario(@PathVariable Long id, Model model) {
        Usuario p = usuarioService.buscarPorId(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Usuario não encontrada, id: " + id
            ));
        model.addAttribute("usuario", p);
        return "usuarios/detalhe";
    }

    @PostMapping("/{id}/excluir")
    public String excluirUsuario(@PathVariable Long id, RedirectAttributes ra) {
        usuarioService.deletarUsuario(id);
        ra.addFlashAttribute("success", "Usuario excluída com sucesso!");
        return "redirect:/usuarios/listar";
    }
}









/*







package com.example.beautystyle.controller; 
 
import jakarta.validation.Valid; 
 
import org.springframework.stereotype.Controller; 
import org.springframework.ui.Model; 
import org.springframework.validation.BindingResult; 
import org.springframework.web.bind.annotation.*; 
import org.springframework.web.server.ResponseStatusException; 
import org.springframework.web.servlet.mvc.support.RedirectAttributes; 
 
 
import com.example.beautystyle.model.Usuario; 
import com.example.beautystyle.service.UsuarioService; 
 
import org.springframework.http.HttpStatus; 
 
@Controller 
@RequestMapping("/usuarios") 
public class UsuarioWebController { 
 
    private final UsuarioService usuarioService; 
 
    public UsuarioWebController(UsuarioService usuarioService) { 
        this.usuarioService = usuarioService; 
    } 
 
    // Mapeia GET /usuarios → redireciona para /usuarios/listar 
    @GetMapping 
    public String index() { 
        return "redirect:/usuarios/listar"; 
    } 
 
    // 1. Página de cadastro 
    @GetMapping("/cadastrar") 
    public String exibirFormCadastro(Model model) { 
        model.addAttribute("usuario", new Usuario()); 
        return "usuarios/form"; 
    } 
 
    @PostMapping("/cadastrar") 
    public String cadastrarUsuario( 
            @Valid @ModelAttribute("usuario") Usuario usuario, 
            BindingResult result, 
            RedirectAttributes ra) { 
 
        if (result.hasErrors()) { 
            // repopula o objeto no formulário em caso de erro 
            return "usuarios/form"; 
        } 
        usuarioService.salvarUsuario(usuario); 
        ra.addFlashAttribute("success", "Usuario cadastrada com sucesso!"); 
        return "redirect:/usuarios/listar"; 
    } 
 
    // 2. Página de listagem 
    @GetMapping("/listar") 
    public String listarUsuarios(Model model) { 
        model.addAttribute("lista", usuarioService.listarUsuarios()); 
        return "usuarios/lista"; 
    } 
 
    // 3. Detalhes e exclusão 
 
    @GetMapping("/{id}") 
    public String detalhesUsuario(@PathVariable Long id, Model model) { 
        Usuario p = usuarioService.buscarPorId(id) 
            .orElseThrow(() -> new ResponseStatusException( 
                HttpStatus.NOT_FOUND, "Usuario não encontrada, id: " + id 
            )); 
        model.addAttribute("usuario", p); 
        return "usuarios/detalhe"; 
    } 
 
    @PostMapping("/{id}/excluir") 
    public String excluirUsuario(@PathVariable Long id, RedirectAttributes ra) { 
        usuarioService.deletarUsuario(id); 
        ra.addFlashAttribute("success", "Usuario excluída com sucesso!"); 
        return "redirect:/usuarios/listar"; 
    } 
} 

*/