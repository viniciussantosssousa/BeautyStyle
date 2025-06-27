package com.salao.agendamentos.repository;

import com.salao.agendamentos.model.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional; // Importe esta anotação

import java.util.Optional;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
    Optional<ConfirmationToken> findByToken(String token);

    // ==============================================================================
    // ======================= MÉTODO ADICIONADO AQUI ===============================
    // ==============================================================================
    @Transactional // Garante que a operação de delete ocorra em uma transação
    void deleteAllByUsuarioId(Long id);
}