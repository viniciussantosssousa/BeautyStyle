package com.example.beautystyle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.beautystyle.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}