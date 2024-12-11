package com.gerenciamentomedico.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


public record PacientesUpdateDTO(
        @Schema(description = "Nome do paciente", example = "Nome Completo")
        @Pattern(regexp = "^$|^[A-Za-zÀ-ÿ]+$", message = "O campo nome não pode conter números.")
        @Size(max = 200, message = "O campo name pode ter até 200 caracteres")
        String nome,

        @Schema(description = "CPF do paciente", example = "000.000.000-00")
        String cpf,

        @Schema(description = "Data de nascimento", example = "dd/mm/aaaa")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        @DateTimeFormat(pattern = "dd-MM-yyyy")
        LocalDate dataDeNascimento,

        @Schema(description = "Contato", example = "00 000000000")
        @Pattern(
                regexp = "^$|^\\(?\\d{2}\\)?[\\s-]?9?[\\s-]?\\d{4}-?\\d{4}$",
                message = "O número de telefone inserido é inválido."
        )
        String contato
) {
}
