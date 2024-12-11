package com.gerenciamentomedico.entities.consultas;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gerenciamentomedico.entities.users.EntityBase;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@SQLDelete(sql = "update consultas set status_da_consulta = 1 where id = ?")
public class Consultas extends EntityBase {

    @Schema(description = "ID do paciente", example = "22ed1a08-cfe8-4833-b8a0-945a0264beb6")
    @Column(nullable = false)
    @NotNull(message = "O campo Paciente ID não pode estar vazio.")
    private UUID pacienteId;

    @Schema(description = "ID do médico", example = "22ed1a08-cfe8-4833-b8a0-945a0264beb6")
    @Column(nullable = false)
    @NotNull(message = "O campo Medico ID não pode estar vazio.")
    private UUID medicoId;

    @Schema(description = "Data e hora da consulta", example = "dd/mm/aaaa")
    @NotNull(message = "O campo data e hora da consulta não pode ser vazio.")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm", timezone = "America/Sao_Paulo")
    private LocalDateTime dataHoraConsulta;

    private Status statusDaConsulta;

    public enum Status {
        AGENDADA,
        CANCELADA,
        CONCLUIDA
    }

    @PrePersist
    public void Persist() {
        this.statusDaConsulta = Status.AGENDADA;
    }

}
