package com.example.beautystyle.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String senha;

    public Usuario() {
    }
 
    public Usuario(String nome, String email, String telefone, String senha){ 
        this.nome = nome; 
        this.email = email; 
        this.telefone = telefone;
        this.senha = senha;
    }
 
    public Long getId() { 
        return id; 
    } 
 
    public void setNome(String nome) { 
        this.nome = nome; 
    } 
 
    public String getNome() { 
        return nome; 
    } 
 
    public void setEmail(String email) { 
        this.email = email; 
    } 
 
    public String getEmail() { 
        return email; 
    } 

        public void setTelefone(String telefone) { 
        this.telefone = telefone; 
    } 
 
    public String getTelefone() { 
        return telefone; 
    } 

    public void setSenha(String senha) { 
        this.senha = senha; 
    } 
 
    public String getSenha() { 
        return senha;
    } 
}







/* 
package com.example.beautystyle.model; 
 
import jakarta.persistence.Column;
import jakarta.persistence.Entity; 
import jakarta.persistence.Table; 
import jakarta.persistence.Id; 
import jakarta.persistence.GeneratedValue; 
import jakarta.persistence.GenerationType; 
 
@Entity 
@Table(name = "usuarios") 
public class Usuario{ 
     
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id; 
 
    private String nome; 

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String telefone;

    private String senha; 
 
    public Usuario() {} 
 
    public Usuario(String nome, String email, String telefone, String senha){ 
        this.nome = nome; 
        this.email = email; 
        this.telefone = telefone;
        this.senha = senha;
    } 
 
    public Long getId() { 
        return id; 
    } 
 
    public void setNome(String nome) { 
        this.nome = nome; 
    } 
 
    public String getNome() { 
        return nome; 
    } 
 
    public void setEmail(String email) { 
        this.email = email; 
    } 
 
    public String getEmail() { 
        return email; 
    } 

        public void setTelefone(String telefone) { 
        this.telefone = telefone; 
    } 
 
    public String getTelefone() { 
        return telefone; 
    } 

    public void setSenha(String senha) { 
        this.senha = senha; 
    } 
 
    public String getSenha() { 
        return senha;
    } 
}

*/