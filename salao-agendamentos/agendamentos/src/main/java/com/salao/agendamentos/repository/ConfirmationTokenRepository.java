package com.salao.agendamentos.repository;

import com.salao.agendamentos.model.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional; 

import java.util.Optional;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
    Optional<ConfirmationToken> findByToken(String token);


    @Transactional 
    void deleteAllByUsuarioId(Long id);
}