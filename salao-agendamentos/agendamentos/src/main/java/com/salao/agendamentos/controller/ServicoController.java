package com.salao.agendamentos.controller;

import com.salao.agendamentos.model.Servico;
import com.salao.agendamentos.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicos")
public class ServicoController {

    @Autowired
    private ServicoRepository servicoRepository;

    @PostMapping
    public Servico criar(@RequestBody Servico servico) {
        return servicoRepository.save(servico);
    }

    @GetMapping
    public List<Servico> listar() {
        return servicoRepository.findAll();
    }
}
