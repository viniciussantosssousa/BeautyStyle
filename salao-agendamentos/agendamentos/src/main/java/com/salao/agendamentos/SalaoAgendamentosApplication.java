package com.salao.agendamentos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync; // <-- IMPORTAR

@SpringBootApplication
@EnableAsync // <-- ADICIONAR
public class SalaoAgendamentosApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalaoAgendamentosApplication.class, args);
	}

}