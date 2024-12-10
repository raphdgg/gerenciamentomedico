package com.gerenciamentomedico.entities.consultas;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gerenciamentomedico.entities.users.EntityBase;
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
@SQLDelete(sql =  "update consultas set status_da_consulta = 1 where id = ?")
public class Consultas extends EntityBase {

    @Column(nullable = false)
    @NotNull(message = "O campo Paciente ID não pode estar vazio.")
    private UUID pacienteId;

    @Column(nullable = false)
    @NotNull(message = "O campo Medico ID não pode estar vazio.")
    private UUID medicoId;

    @NotNull(message = "O campo data e hora da consulta não pode ser vazio.")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm", timezone = "America/Sao_Paulo")
    private LocalDateTime dataHoraConsulta;

    private Status statusDaConsulta;

    public enum Status{
        AGENDADA,
        CANCELADA,
        CONCLUIDA
    }

    @PrePersist
    public void Persist() {
        this.statusDaConsulta = Status.AGENDADA;
    }

}
