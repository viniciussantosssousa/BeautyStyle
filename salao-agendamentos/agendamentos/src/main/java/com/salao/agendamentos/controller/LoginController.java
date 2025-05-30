package com.salao.agendamentos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String exibirPaginaLogin() {
        return "login"; // Isso carrega o arquivo login.html da pasta templates
    }
}
