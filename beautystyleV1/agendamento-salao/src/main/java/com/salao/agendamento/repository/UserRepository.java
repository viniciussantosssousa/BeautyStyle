package com.salao.agendamento.repository;

import com.salao.agendamento.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // Indica que esta interface é um componente de repositório Spring
public interface UserRepository extends JpaRepository<User, Long> {
    // O Spring Data JPA automaticamente implementa este método com base no nome
    Optional<User> findByEmail(String email);
}