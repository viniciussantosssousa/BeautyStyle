package com.salao.agendamento.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // Indica que esta classe é um controlador Spring MVC
public class WebController {

    @GetMapping("/home") // Mapeia requisições GET para /home
    public String home() {
        return "home"; // Retorna o nome do template Thymeleaf (home.html)
    }

    @GetMapping("/login") // Mapeia requisições GET para /login (usado pelo Spring Security)
    public String login() {
        return "login"; // Retorna o nome do template Thymeleaf (login.html)
    }

    @GetMapping("/dashboard") // Mapeia requisições GET para /dashboard (página após login)
    public String dashboard() {
        return "dashboard"; // Retorna o nome do template Thymeleaf (dashboard.html)
    }

    @GetMapping("/admin") // Mapeia requisições GET para /admin (apenas para ROLE_ADMIN)
    public String admin() {
        return "admin"; // Retorna o nome do template Thymeleaf (admin.html)
    }
}