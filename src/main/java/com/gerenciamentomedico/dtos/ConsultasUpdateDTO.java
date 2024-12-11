package com.gerenciamentomedico.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;


public record ConsultasUpdateDTO(

        @Schema(description = "ID do paciente", example = "22ed1a08-cfe8-4833-b8a0-945a0264beb6")
        UUID pacienteId,

        @Schema(description = "ID do médico", example = "22ed1a08-cfe8-4833-b8a0-945a0264beb6")
        UUID medicoId,

        @Schema(description = "Data e hora da consulta", example = "dd/mm/aaaa")
        @JsonFormat(pattern = "dd-MM-yyyy HH:mm", timezone = "America/Sao_Paulo")
        LocalDateTime dataHoraConsulta

) {

}
