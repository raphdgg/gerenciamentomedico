package com.gerenciamentomedico.controller;

import com.gerenciamentomedico.entities.users.Medicos;
import com.gerenciamentomedico.dtos.MedicosUpdateDTO;
import com.gerenciamentomedico.services.MedicosService;
import com.gerenciamentomedico.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Médicos")
@RestController
@RequestMapping("/medicos")
@Validated
public class MedicosController {

    @Autowired
    private MedicosService medicosService;

    @Operation(summary = "Lista todos os médicos cadastrados", description = "Este endpoint retorna uma lista com todos os médicos cadastrados e ativos")
    @GetMapping
    public ResponseEntity<List<Medicos>> list(@ModelAttribute Medicos medico) {
        medicosService.listMedicos(medico);
        return ResponseEntity.ok(medicosService.listMedicos(medico));
    }

    @Operation(summary = "Busca um médico pelo seu ID", description = "Este endpoint retorna um médico específico com base no ID")
    @GetMapping("/{id}")
    public ResponseEntity<Medicos> getMedicosById(@PathVariable UUID id) {
        medicosService.getMedicosById(id);
        return ResponseEntity.ok(medicosService.getMedicosById(id));
    }

    @Operation(summary = "Cria um novo médico", description = "Este endpoint criará um novo médico")
    @PostMapping
    public ResponseEntity<Medicos> addMedicos(@RequestBody @Valid Medicos medico) {
        medicosService.addMedicos(medico);
        return ResponseEntity.ok(medicosService.addMedicos(medico));
    }

    @Operation(summary = "Atualiza as informações do médico", description = "Este endpoint atualiza as informações de um médico em específico com base no ID")
    @PutMapping("/{id}")
    public ResponseEntity<Medicos> updateMedicosById(@PathVariable UUID id, @RequestBody @Valid MedicosUpdateDTO medicoDetails) {
        medicosService.updateMedicosById(id, medicoDetails);
        return ResponseEntity.ok(medicosService.updateMedicosById(id, medicoDetails));
    }

    @Operation(summary = "Inativa o cadastro de um médico", description = "Este endpoint inativa o cadastro de um médico em específico com base no ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> inactivateMedicosById(@PathVariable UUID id) {
        medicosService.inactivateMedicosById(id);
        return ResponseEntity.ok(new ApiResponse("Cadastro desativado com sucesso."));
    }


}

