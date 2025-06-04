package com.salao.agendamentos.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import com.salao.agendamentos.dto.AgendamentoDTO;
import com.salao.agendamentos.model.Agendamento;
import com.salao.agendamentos.service.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoService service;

    @PostMapping
    public Agendamento agendar(@RequestBody AgendamentoDTO dto) {
        return service.agendar(dto);
    }

    @GetMapping
    public List<Agendamento> listarTodos() {
        return service.listarTodos();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancelar(@PathVariable Long id) {
        try {
            service.cancelar(id);
            return ResponseEntity.ok("Agendamento cancelado com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
