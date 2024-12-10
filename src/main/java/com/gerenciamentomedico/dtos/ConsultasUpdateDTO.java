package com.gerenciamentomedico.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public record ConsultasUpdateDTO(

        UUID pacienteId,

        UUID medicoId,

        //@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
        @JsonFormat(pattern = "dd-MM-yyyy HH:mm", timezone = "America/Sao_Paulo")
        LocalDateTime dataHoraConsulta

) {

}
