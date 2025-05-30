package com.salao.agendamentos.controller;

import com.salao.agendamentos.dto.AgendamentoDTO;
import com.salao.agendamentos.service.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.salao.agendamentos.model.Agendamento;

@Controller
public class PaginaAgendamentoController {

    @Autowired
    private AgendamentoService service;

    @GetMapping("/agendar")
    public String exibirFormulario() {
        return "agendamento";
    }

    @PostMapping("/agendar")
    public String processarFormulario(@RequestParam String nomeCliente,
                                      @RequestParam String servico,
                                      @RequestParam String dataHora,
                                      @RequestParam String telefone,
                                      Model model) {
        try {
            AgendamentoDTO dto = new AgendamentoDTO();
            dto.setNomeCliente(nomeCliente);
            dto.setServico(servico);
            dto.setTelefone(telefone);
            dto.setDataHora(java.time.LocalDateTime.parse(dataHora));

            service.agendar(dto);
            model.addAttribute("mensagem", "Agendamento realizado com sucesso!");

        } catch (Exception e) {
            model.addAttribute("mensagem", "Erro: " + e.getMessage());
        }

        return "lista-agendamentos";
    }

    @GetMapping("/agendamentos")
    public String listarAgendamentos(Model model) {
        model.addAttribute("agendamentos", service.listarTodos());
        return "lista-agendamentos";
    }

    @DeleteMapping("/cancelar/{id}")
    public String cancelarAgendamento(@PathVariable Long id, Model model) {
        try {
            service.cancelar(id);
            model.addAttribute("mensagem", "Agendamento cancelado com sucesso.");
        } catch (Exception e) {
            model.addAttribute("mensagem", "Erro ao cancelar: " + e.getMessage());
        }
        model.addAttribute("agendamentos", service.listarTodos());
        return "lista-agendamentos";
    }

    @GetMapping("/editar/{id}")
    public String editarFormulario(@PathVariable Long id, Model model) {
        Agendamento agendamento = service.buscarPorId(id);
        model.addAttribute("agendamento", agendamento);
        return "form-edicao";
    }

    @PostMapping("/editar/{id}")
    public String salvarEdicao(@PathVariable Long id, @ModelAttribute Agendamento agendamentoEditado, Model model) {
        service.atualizar(id, agendamentoEditado);
        model.addAttribute("mensagem", "Agendamento atualizado com sucesso!");
        model.addAttribute("agendamentos", service.listarTodos());
        return "lista-agendamentos";
    }
}
