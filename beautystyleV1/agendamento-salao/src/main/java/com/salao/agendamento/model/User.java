package com.salao.agendamento.model;

import jakarta.persistence.*;
import lombok.Data; // Anotação do Lombok para getters, setters, etc.
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity // Indica que esta classe é uma entidade JPA e será mapeada para uma tabela no DB
@Table(name = "users") // Define o nome da tabela no banco de dados
@Data // Lombok: Gera getters, setters, toString, equals e hashCode
@NoArgsConstructor // Lombok: Gera um construtor sem argumentos
@AllArgsConstructor // Lombok: Gera um construtor com todos os argumentos
public class User {

    @Id // Indica que este é o campo da chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configura a geração automática de ID pelo DB
    private Long id;

    @Column(nullable = false, unique = true) // Campo obrigatório e único
    private String email;

    @Column(nullable = false) // Campo obrigatório
    private String password;

    @Column(nullable = false) // Campo obrigatório para definir o perfil (ROLE_CLIENTE, ROLE_ADMIN)
    private String role; // Ex: "ROLE_CLIENTE", "ROLE_ADMIN"

    @Column(nullable = false)
    private boolean enabled = true; // Para ativar/desativar o usuário

    // Construtor adicional para conveniência
    public User(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.enabled = true;
    }
}