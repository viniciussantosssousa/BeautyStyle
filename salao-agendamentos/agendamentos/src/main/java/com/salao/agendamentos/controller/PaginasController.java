package com.salao.agendamentos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaginasController {

    @GetMapping("/cadastro")
    public String mostrarCadastro() {
        return "cadastro";
    }

    @GetMapping("/login")
    public String exibirPaginaLogin() {
        return "login"; 
    }

}
