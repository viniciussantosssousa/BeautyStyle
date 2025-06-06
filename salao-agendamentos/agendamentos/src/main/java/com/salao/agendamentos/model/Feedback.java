package com.salao.agendamentos.model;

public class Feedback {
    
    private static long idCounter = 0L; // Um contador para gerar IDs únicos

    private final Long id; // ID único para cada feedback
    private String nome;
    private String mensagem;

    // Construtor que atribui um ID único automaticamente
    public Feedback() {
        this.id = idCounter++;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}