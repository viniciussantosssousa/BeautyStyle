package com.salao.agendamentos.controller;

import com.salao.agendamentos.model.Feedback;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class FeedbackController {

    public static List<Feedback> feedbacksRecebidos = new ArrayList<>();

    @GetMapping("/feedback")
    public String exibirPaginaFeedback(Model model) {
        List<Feedback> feedbacksInvertidos = new ArrayList<>(feedbacksRecebidos);
        Collections.reverse(feedbacksInvertidos);
        model.addAttribute("feedbacks", feedbacksInvertidos);
        model.addAttribute("novoFeedback", new Feedback());
        return "home";
    }

    @PostMapping("/feedback")
    public String processarFeedback(@ModelAttribute Feedback feedback, Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String nomeUsuario = userDetails.getUsername();
            feedback.setNome(nomeUsuario);
        } else {
            feedback.setNome("Anónimo");
        }
        
        feedbacksRecebidos.add(feedback);
        return "redirect:/feedback";
    } 

    @PostMapping("/feedback/excluir/{id}")
    public String excluirFeedback(@PathVariable("id") Long id) {
        // Usamos o método removeIf para encontrar e remover o feedback com o ID correspondente
        feedbacksRecebidos.removeIf(fb -> fb.getId().equals(id));
        
        // Redirecionamos de volta para a página
        return "redirect:/feedback";
    }
}