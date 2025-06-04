package com.salao.agendamentos.repository;

import com.salao.agendamentos.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    Optional<Agendamento> findByDataHora(LocalDateTime dataHora);
}
