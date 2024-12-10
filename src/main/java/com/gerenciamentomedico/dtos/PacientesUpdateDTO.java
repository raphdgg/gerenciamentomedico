package com.gerenciamentomedico.dtos;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


public record PacientesUpdateDTO(
        @Pattern(regexp = "^$|^[A-Za-zÀ-ÿ]+$", message = "O campo nome não pode conter números.")
        @Size(max = 200, message = "O campo name pode ter até 200 caracteres")
        String nome,

        String cpf,

        @DateTimeFormat(pattern = "dd-MM-yyyy")
        LocalDate dataDeNascimento,

        @Pattern(
                regexp = "^$|^\\(?\\d{2}\\)?[\\s-]?9?[\\s-]?\\d{4}-?\\d{4}$",
                message = "O número de telefone inserido é inválido."
        )
        String contato
) {
}
