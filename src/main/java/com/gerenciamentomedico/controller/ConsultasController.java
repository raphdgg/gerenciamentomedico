package com.gerenciamentomedico.controller;


import com.gerenciamentomedico.dtos.ConsultasUpdateDTO;
import com.gerenciamentomedico.entities.consultas.Consultas;
import com.gerenciamentomedico.services.ConsultasService;
import com.gerenciamentomedico.utils.ApiResp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Tag(name = "Consultas")
@RestController
@RequestMapping("/consultas")
@Validated

public class ConsultasController {

    @Autowired
    private ConsultasService consultasService;

    @Operation(summary = "Lista todas as consultas cadastradas", description = "Este endpoint retorna uma lista de todas as consultas cadastradas")
    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de consultas feita com sucesso", content = @Content(examples = @ExampleObject(value = "[{\"id\":\"7b126efb-1e55-43c5-ade4-b2167fbadb2f\",\n\"dataDeCriacao\":\"10-12-2024 20:49:55\",\n\"pacienteId\":\"123e4567-e89b-12d3-a456-426655440000\",\n\"medicoId\":\"22ed1a08-cfe8-4833-b8a0-945a0264beb6\",\n\"dataHoraConsulta\":\"12-12-2024 10:30\",\n\"statusDaConsulta\":\"AGENDADA\"}]"))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(examples = @ExampleObject(value = "[{\"status\":\"BAD_REQUEST\",\n\"message\":\"Não foram encontradas consultas agendadas para parâmetro escolhido.\"}]"))),
            @ApiResponse(responseCode = "404", description = "Consulta não encontrada", content = @Content(examples = @ExampleObject(value = "[{\"status\":\"NOT_FOUND\",\n\"message\":\"Consulta não encontrada com o id: <id>\"}]"))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(examples = @ExampleObject(value = "[{\"status\":\"INTERNAL_SERVER_ERROR\",\n\"message\":\"Ocorreu um erro inesperado.\"}]")))
    })
    public ResponseEntity<List<Consultas>> list(@RequestParam(required = false) LocalDateTime dataHoraConsulta, @ModelAttribute Consultas consulta) {
        consultasService.listConsultas(consulta);
        return ResponseEntity.ok(consultasService.listConsultas(consulta));
    }

    @Operation(summary = "Busca uma consulta pelo seu ID", description = "Este endpoint retorna uma consulta específico com base no ID")
    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta encontrada", content = @Content(examples = @ExampleObject(value = "[{\"id\":\"7b126efb-1e55-43c5-ade4-b2167fbadb2f\",\n\"dataDeCriacao\":\"10-12-2024 20:49:55\",\n\"pacienteId\":\"123e4567-e89b-12d3-a456-426655440000\",\n\"medicoId\":\"22ed1a08-cfe8-4833-b8a0-945a0264beb6\",\n\"dataHoraConsulta\":\"12-12-2024 10:30\",\n\"statusDaConsulta\":\"AGENDADA\"}]"))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(examples = @ExampleObject(value = "[{\"status\":\"BAD_REQUEST\",\n\"message\":\"O id inserido não é um UUID.\"}]"))),
            @ApiResponse(responseCode = "404", description = "Consulta não encontrada", content = @Content(examples = @ExampleObject(value = "[{\"status\":\"NOT_FOUND\",\n\"message\":\"Consulta não encontrada com o id: <id>\"}]"))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(examples = @ExampleObject(value = "[{\"status\":\"INTERNAL_SERVER_ERROR\",\n\"message\":\"Ocorreu um erro inesperado.\"}]")))
    })
    public ResponseEntity<Consultas> getConsultasById(@PathVariable UUID id) {
        consultasService.getConsultasById(id);
        return ResponseEntity.ok(consultasService.getConsultasById(id));
    }

    @Operation(summary = "Agenda uma nova consulta", description = "Este endpoint agendará uma nova consulta")
    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Consulta criada com sucesso", content = @Content(examples = @ExampleObject(value = "[{\"id\":\"7b126efb-1e55-43c5-ade4-b2167fbadb2f\",\n\"dataDeCriacao\":\"10-12-2024 20:49:55\",\n\"pacienteId\":\"123e4567-e89b-12d3-a456-426655440000\",\n\"medicoId\":\"22ed1a08-cfe8-4833-b8a0-945a0264beb6\",\n\"dataHoraConsulta\":\"12-12-2024 10:30\",\n\"statusDaConsulta\":\"AGENDADA\"}]"))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(examples = @ExampleObject(value = "[{\"status\":\"BAD_REQUEST\",\n\"message\":\"O id inserido não é um UUID.\"}]"))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(examples = @ExampleObject(value = "[{\"status\":\"INTERNAL_SERVER_ERROR\",\n\"message\":\"Ocorreu um erro inesperado.\"}]")))
    })
    public ResponseEntity<Consultas> addConsultasById(@RequestBody @Valid Consultas consulta) {
        consultasService.addConsultas(consulta);
        return ResponseEntity.ok(consultasService.addConsultas(consulta));
    }

    @Operation(summary = "Atualiza as informações da consulta", description = "Este endpoint atualiza as informações de uma consulta em específico com base no ID")
    @PutMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta atualizada", content = @Content(examples = @ExampleObject(value = "[{\"id\":\"7b126efb-1e55-43c5-ade4-b2167fbadb2f\",\n\"dataDeCriacao\":\"10-12-2024 20:49:55\",\"pacienteId\":\"123e4567-e89b-12d3-a456-426655440000\",\n\"medicoId\":\"22ed1a08-cfe8-4833-b8a0-945a0264beb6\",\n\"dataHoraConsulta\":\"12-12-2024 10:30\",\n\"statusDaConsulta\":\"AGENDADA\"}]"))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(examples = @ExampleObject(value = "[{\"pacienteId\":\"texto\",\n\"medicoId\":\"texto\",\n\"dataHoraConsulta\":\"12/12/2024 10h30\"}]"))),
            @ApiResponse(responseCode = "404", description = "Consulta não encontrada para ser atualizada", content = @Content(examples = @ExampleObject(value = "[{\"status\":\"NOT_FOUND\",\n\"message\":\"Paciente com o id: <id> não encontrado.\"}]"))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(examples = @ExampleObject(value = "[{\"status\":\"INTERNAL_SERVER_ERROR\",\n\"message\":\"Ocorreu um erro inesperado.\"}]")))

    })
    @Transactional
    public ResponseEntity<Consultas> updateById(@PathVariable UUID id, @RequestBody @Valid ConsultasUpdateDTO consultaDetails) {
        consultasService.updateConsultasById(id, consultaDetails);
        return ResponseEntity.ok(consultasService.updateConsultasById(id, consultaDetails));
    }

    @Operation(summary = "Conclui uma consulta agendada", description = "Este endpoint conclui uma consulta agendada com base no ID")
    @PutMapping("/concluir/{id}")
    @Transactional
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta concluída", content = @Content(examples = @ExampleObject(value = "[{\"message\":\"Consulta concluída com sucesso.\"}]"))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(examples = @ExampleObject(value = "[{\"status\":\"BAD_REQUEST\",\n\"message\":\"O id inserido não é um UUID.\"}]"))),
            @ApiResponse(responseCode = "404", description = "Consulta não encontrada para ser atualizada", content = @Content(examples = @ExampleObject(value = "[{\"status\":\"NOT_FOUND\",\n\"message\":\"Não foi encontrado nenhuma consulta com o id: <id>\"}]"))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(examples = @ExampleObject(value = "[{\"status\":\"INTERNAL_SERVER_ERROR\",\n\"message\":\"Ocorreu um erro inesperado.\"}]")))
    })
    public ResponseEntity<ApiResp> concludeById(@PathVariable @Valid UUID id) {
        consultasService.concludeConsultasById(id);
        return ResponseEntity.ok(new ApiResp("Consulta concluída com sucesso."));
    }

    @Operation(summary = "Cancela uma consulta agendada", description = "Este endpoint cancela uma consulta agendada com base no ID")
    @DeleteMapping("/cancelar/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta cancelada", content = @Content(examples = @ExampleObject(value = "[{\"message\":\"Consulta cancelada com sucesso.\"}]"))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(examples = @ExampleObject(value = "[{\"status\":\"BAD_REQUEST\",\n\"message\":\"O id inserido não é um UUID.\"}]"))),
            @ApiResponse(responseCode = "404", description = "Consulta não encontrada para ser atualizada", content = @Content(examples = @ExampleObject(value = "[{\"status\":\"NOT_FOUND\",\n\"message\":\"Não foi encontrado nenhuma consulta com o id: <id>\"}]"))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(examples = @ExampleObject(value = "[{\"status\":\"INTERNAL_SERVER_ERROR\",\n\"message\":\"Ocorreu um erro inesperado.\"}]")))
    })
    public ResponseEntity<ApiResp> cancelById(@PathVariable @Valid UUID id) {
        consultasService.cancelConsultasById(id);
        return ResponseEntity.ok(new ApiResp("Consulta cancelada com sucesso."));
    }

}

