package com.gerenciamentomedico.controller;


import com.gerenciamentomedico.entities.consultas.Consultas;
import com.gerenciamentomedico.dtos.ConsultasUpdateDTO;
import com.gerenciamentomedico.services.ConsultasService;
import com.gerenciamentomedico.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
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
    public ResponseEntity<List<Consultas>> list(@RequestParam(required = false) LocalDateTime dataHoraConsulta, @ModelAttribute Consultas consulta) {
        consultasService.listConsultas(consulta);
        return ResponseEntity.ok(consultasService.listConsultas(consulta));
    }
    @Operation(summary = "Busca uma consulta pelo seu ID", description = "Este endpoint retorna uma consulta específico com base no ID")
    @GetMapping("/{id}")
    public ResponseEntity<Consultas> getConsultasById(@PathVariable UUID id) {
        consultasService.getConsultasById(id);
        return ResponseEntity.ok(consultasService.getConsultasById(id));
    }

    @Operation(summary = "Agenda uma nova consulta", description = "Este endpoint agendará uma nova consulta")
    @PostMapping
    public ResponseEntity<Consultas> addConsultasById(@RequestBody @Valid Consultas consulta) {
        consultasService.addConsultas(consulta);
        return ResponseEntity.ok(consultasService.addConsultas(consulta));
    }

    @Operation(summary = "Atualiza as informações da consulta", description = "Este endpoint atualiza as informações de uma consulta em específico com base no ID")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Consultas> updateById(@PathVariable UUID id, @RequestBody @Valid ConsultasUpdateDTO consultaDetails) {
        consultasService.updateConsultasById(id, consultaDetails);
        return ResponseEntity.ok(consultasService.updateConsultasById(id, consultaDetails));
    }

    @Operation(summary = "Conclui uma consulta agendada", description = "Este endpoint conclui uma consulta agendada com base no ID")
    @PutMapping("/concluir/{id}")
    @Transactional
    public ResponseEntity<ApiResponse> concludeById(@PathVariable @Valid UUID id) {
        consultasService.concludeConsultasById(id);
        return ResponseEntity.ok(new ApiResponse("Consulta concluída com sucesso."));
    }

    @Operation(summary = "Cancela uma consulta agendada", description = "Este endpoint cancela uma consulta agendada com base no ID")
    @DeleteMapping("/cancelar/{id}")
    public ResponseEntity<ApiResponse> cancelById(@PathVariable @Valid UUID id) {
        consultasService.cancelConsultasById(id);
        return ResponseEntity.ok(new ApiResponse("Consulta cancelada com sucesso."));
    }

}

