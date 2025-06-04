package com.salao.agendamentos.controller;

import com.salao.agendamentos.dto.AgendamentoDTO;
import com.salao.agendamentos.service.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.salao.agendamentos.model.Agendamento;
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // Importe RedirectAttributes

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
                                      RedirectAttributes ra) { // Adicione RedirectAttributes aqui
        try {
            AgendamentoDTO dto = new AgendamentoDTO();
            dto.setNomeCliente(nomeCliente);
            dto.setServico(servico);
            dto.setTelefone(telefone);
            dto.setDataHora(java.time.LocalDateTime.parse(dataHora));

            service.agendar(dto);
            ra.addFlashAttribute("mensagem", "Agendamento realizado com sucesso!"); // Adiciona a mensagem flash
        } catch (Exception e) {
            ra.addFlashAttribute("erro", "Erro: " + e.getMessage()); // Adiciona mensagem de erro flash
        }

        return "redirect:/agendamentos"; // Redireciona para a lista de agendamentos
    }

    @GetMapping("/agendamentos")
    public String listarAgendamentos(Model model) {
        model.addAttribute("agendamentos", service.listarTodos());
        // Mensagens flash serão automaticamente adicionadas ao Model aqui se existirem
        return "lista-agendamentos";
    }

    @DeleteMapping("/cancelar/{id}")
    public String cancelarAgendamento(@PathVariable Long id, RedirectAttributes ra) { // Use RedirectAttributes
        try {
            service.cancelar(id);
            ra.addFlashAttribute("mensagem", "Agendamento cancelado com sucesso."); // Mensagem flash
        } catch (Exception e) {
            ra.addFlashAttribute("erro", "Erro ao cancelar: " + e.getMessage()); // Mensagem de erro flash
        }
        return "redirect:/agendamentos"; // Redireciona para a lista
    }

    @GetMapping("/editar/{id}")
    public String editarFormulario(@PathVariable Long id, Model model) {
        Agendamento agendamento = service.buscarPorId(id);
        model.addAttribute("agendamento", agendamento);
        return "form-edicao";
    }

    @PostMapping("/editar/{id}")
    public String salvarEdicao(@PathVariable Long id, @ModelAttribute Agendamento agendamentoEditado, RedirectAttributes ra) { // Use RedirectAttributes
        service.atualizar(id, agendamentoEditado);
        ra.addFlashAttribute("mensagem", "Agendamento atualizado com sucesso!"); // Mensagem flash
        return "redirect:/agendamentos"; // Redireciona para a lista
    }
}