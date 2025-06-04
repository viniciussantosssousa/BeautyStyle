package com.salao.agendamentos.repository;

import com.salao.agendamentos.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicoRepository extends JpaRepository<Servico, Long> {
}
