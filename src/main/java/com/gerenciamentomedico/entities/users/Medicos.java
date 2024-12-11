package com.gerenciamentomedico.entities.users;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Where(clause = "active = true")
@SQLDelete(sql = "update medicos set active = false where id = ?")
public class Medicos extends EntityBase {

    @Schema(hidden = true)
    @Column(nullable = false)
    Boolean active = true;

    @Schema(description = "Nome", example = "Nome Completo")
    @NotBlank(message = "O campo nome não pode ser vazio.")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ]+( [A-Za-zÀ-ÿ]+)*$", message = "O campo nome não pode ser vazio ou conter números.")
    @Size(max = 200, message = "O campo nome pode ter até 200 caracteres.")
    @Column(nullable = false)
    private String nome;

    @Schema(description = "Especialidade", example = "Especialidade")
    @NotBlank(message = "O campo especialidade não pode ser vazio.")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ]+$", message = "O campo especialidade não pode ser vazio ou conter números.")
    @Size(max = 200, message = "O campo especialidade pode ter até 200 caracteres.")
    private String especialidade;

    @Schema(description = "CRM", example = "000000-UF")
    @NotBlank(message = "O campo CRM não pode ser vazio")
    @Column(unique = true)
    @Pattern(
            regexp = "^\\d{4,6}-[A-Z]{2}$",
            message = "O CRM deve seguir o formato '000000-UF', com 4 a 6 dígitos seguidos por um hífen e a sigla do estado em letras maiúsculas."
    )
    private String crm;

    @Schema(description = "e-mail", example = "example@email.com")
    @NotBlank(message = "O campo e-mail não pode ser vazio.")
    @Column(unique = true)
    @Email(message = "O campo precisa ser um e-mail")
    private String email;
}
