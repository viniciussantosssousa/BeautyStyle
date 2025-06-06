package com.salao.agendamentos.controller;

import com.salao.agendamentos.model.Feedback; // IMPORTANTE: Importe a sua classe Feedback
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // IMPORTANTE: Importe a classe Model
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class HomeController {

    @GetMapping("/")
    public String redirecionarParaHome() {
        return "redirect:/home";
    }

    // AQUI ESTÁ A GRANDE CORREÇÃO
    @GetMapping("/home")
    public String mostrarHome(Model model) { // 1. Adicionamos o Model como parâmetro

        // 2. Adicionamos a lista de feedbacks existentes ao modelo.
        // Usamos a lista estática do FeedbackController para que os dados sejam consistentes.
        List<Feedback> feedbacksInvertidos = new ArrayList<>(FeedbackController.feedbacksRecebidos);
        Collections.reverse(feedbacksInvertidos);
        model.addAttribute("feedbacks", feedbacksInvertidos);

        // 3. Adicionamos um objeto Feedback em branco para o formulário.
        model.addAttribute("novoFeedback", new Feedback());

        // 4. Agora sim, retornamos a página.
        return "home";
    }
}