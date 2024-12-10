package com.gerenciamentomedico.controller;

import com.gerenciamentomedico.entities.users.Pacientes;
import com.gerenciamentomedico.dtos.PacientesUpdateDTO;
import com.gerenciamentomedico.services.PacientesService;
import com.gerenciamentomedico.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Pacientes")
@RestController
@RequestMapping("/pacientes")
@Validated
public class PacientesController {

    @Autowired
    private PacientesService pacientesService;

    @Operation(summary = "Lista todos os pacientes cadastrados", description = "Este endpoint retorna uma lista com todos os pacientes cadastrados e ativos")
    @GetMapping
    public ResponseEntity<List<Pacientes>> listPacientes(@ModelAttribute Pacientes paciente) {
        pacientesService.listPacientes(paciente);
        return ResponseEntity.ok(pacientesService.listPacientes(paciente));
    }

    @Operation(summary = "Busca um paciente pelo seu ID", description = "Este endpoint retorna um paciente específico com base no ID")
    @GetMapping("/{id}")
    public ResponseEntity<Pacientes> getPacientesById(@PathVariable UUID id) {
        pacientesService.getByPacientesId(id);
        return ResponseEntity.ok(pacientesService.getByPacientesId(id));
    }

    @Operation(summary = "Cria um novo paciente", description = "Este endpoint criará um novo paciente")
    @PostMapping
    @Transactional
    public ResponseEntity<Pacientes> addPacientes(@RequestBody @Valid Pacientes paciente) {
        pacientesService.addPacientes(paciente);
        return ResponseEntity.status(HttpStatus.CREATED).body(pacientesService.addPacientes(paciente));
    }

    @Operation(summary = "Atualiza as informações do paciente", description = "Este endpoint atualiza as informações de um paciente em específico com base no ID")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Pacientes> updatePacientesById(@PathVariable UUID id, @Valid @RequestBody PacientesUpdateDTO pacienteDetails) {
        pacientesService.updatePacientesById(id, pacienteDetails);
        return ResponseEntity.status(HttpStatus.OK).body(pacientesService.updatePacientesById(id, pacienteDetails));
    }

    @Operation(summary = "Inativa o cadastro de um paciente", description = "Este endpoint inativa o cadastro de um paciente em específico com base no ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> inactivatePacientesById(@PathVariable UUID id) {
        pacientesService.inactivatePacientesById(id);
        return ResponseEntity.ok(new ApiResponse("Cadastro desativado com sucesso."));
    }
}