package com.gerenciamentomedico.entities.users;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Where(clause = "active = true")
@SQLDelete(sql =  "update pacientes set active = false where id = ?")
public class Pacientes extends EntityBase {

    @Column(nullable = false)
    Boolean active = true;

    @NotBlank(message = "O campo nome não pode ser vazio.")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ]+( [A-Za-zÀ-ÿ]+)*$", message = "O campo nome não pode ser vazio ou conter números.")
    @Size(max = 200, message = "O campo nome pode ter até 200 caracteres")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "O campo CPF não pode ser vazio.")
    @Column(unique = true, nullable = false)
    @CPF(message = "CPF inválido")
    private String cpf;

    @NotNull(message = "O campo data de nascimento não pode ser vazio.")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dataDeNascimento;

    @NotBlank(message = "O campo contato não pode ser vazio.")
    @Column(unique = true, nullable = false)
    @Pattern(
            regexp = "^$|^\\(?\\d{2}\\)?[\\s-]?9?[\\s-]?\\d{4}-?\\d{4}$",
            message = "Número de telefone inválido."
    )
    private String contato;


}
