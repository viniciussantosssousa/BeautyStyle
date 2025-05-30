package com.salao.agendamentos.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private String nome;
    private String email;
    private String telefone;
    private String senha;
    private String tipo;
}
