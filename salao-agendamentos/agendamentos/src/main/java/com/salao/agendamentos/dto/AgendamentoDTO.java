package com.salao.agendamentos.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AgendamentoDTO {
    private String nomeCliente;
    private String servico;
    private LocalDateTime dataHora;
    private String telefone;
}
