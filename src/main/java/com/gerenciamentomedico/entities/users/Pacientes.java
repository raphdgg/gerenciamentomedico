package com.gerenciamentomedico.entities.users;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(hidden = true)
    @Column(nullable = false)
    Boolean active = true;

    @Schema(description = "Nome do paciente", example = "Nome Completo")
    @NotBlank(message = "O campo nome não pode ser vazio.")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ]+( [A-Za-zÀ-ÿ]+)*$", message = "O campo nome não pode ser vazio ou conter números.")
    @Size(max = 200, message = "O campo nome pode ter até 200 caracteres")
    @Column(nullable = false)
    private String nome;

    @Schema(description = "CPF do paciente", example = "000.000.000-00")
    @NotBlank(message = "O campo CPF não pode ser vazio.")
    @Column(unique = true, nullable = false)
    @CPF(message = "CPF inválido")
    private String cpf;

    @Schema(description = "Data de nascimento", example = "dd/mm/aaaa")
    @NotNull(message = "O campo data de nascimento não pode ser vazio.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dataDeNascimento;

    @Schema(description = "Contato", example = "00 000000000")
    @NotBlank(message = "O campo contato não pode ser vazio.")
    @Column(unique = true, nullable = false)
    @Pattern(
            regexp = "^$|^\\(?\\d{2}\\)?[\\s-]?9?[\\s-]?\\d{4}-?\\d{4}$",
            message = "Número de telefone inválido."
    )
    private String contato;


}
