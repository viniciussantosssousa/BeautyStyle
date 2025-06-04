package com.salao.agendamentos.service;

import java.util.List;
import com.salao.agendamentos.dto.AgendamentoDTO;
import com.salao.agendamentos.model.Agendamento;
import com.salao.agendamentos.repository.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository repository;

    public Agendamento agendar(AgendamentoDTO dto) {
        Optional<Agendamento> existente = repository.findByDataHora(dto.getDataHora());

        if (existente.isPresent()) {
            throw new RuntimeException("Horário já agendado!");
        }

        Agendamento novo = Agendamento.builder()
                .nomeCliente(dto.getNomeCliente())
                .servico(dto.getServico())
                .dataHora(dto.getDataHora())
                .telefone(dto.getTelefone())
                .build();

        return repository.save(novo);
    }

    public List<Agendamento> listarTodos() {
        return repository.findAll();
    }

    public void cancelar(Long id) {
        if (!repository.existsById(id)) {
        throw new RuntimeException("Agendamento não encontrado.");
        }
        repository.deleteById(id);
    }

    public Agendamento buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));
    }

    public void atualizar(Long id, Agendamento atualizado) {
        Agendamento existente = buscarPorId(id);
        existente.setNomeCliente(atualizado.getNomeCliente());
        existente.setServico(atualizado.getServico());
        existente.setTelefone(atualizado.getTelefone());
        existente.setDataHora(atualizado.getDataHora());
        repository.save(existente);
    }
}
