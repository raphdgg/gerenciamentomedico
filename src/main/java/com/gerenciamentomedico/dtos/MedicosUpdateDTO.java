package com.gerenciamentomedico.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

//@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record MedicosUpdateDTO(

        @Pattern(regexp = "^$|^[A-Za-zÀ-ÿ]+$", message = "O campo nome não pode conter números.")
        @Size(max = 200, message = "O campo nome pode ter até 200 caracteres")
        String nome,

        @Pattern(
                regexp = "^$|^[A-Za-z]{2}\\d{4,6}$",
                message = "O CRM deve seguir o formato '000000-UF', com 4 a 6 dígitos seguidos por um hífen e a sigla do estado em letras maiúsculas."
        )
        String crm,

        @Size(max = 200, message = "O campo especialidade pode ter até 200 caracteres.")
        @Pattern(regexp = "^$|^[A-Za-zÀ-ÿ]+$", message = "O campo especialidade não pode conter números.")
        String especialidade,

        @Size(max = 200, message = "O campo e-mail pode ter até 200 caracteres.")
        @Email(message = "O campo precisa ser um e-mail")
        String email
) {
}
