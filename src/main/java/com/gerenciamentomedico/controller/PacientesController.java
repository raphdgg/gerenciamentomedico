package com.gerenciamentomedico.controller;

import com.gerenciamentomedico.dtos.PacientesUpdateDTO;
import com.gerenciamentomedico.entities.users.Pacientes;
import com.gerenciamentomedico.services.PacientesService;
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pacientes feita com sucesso", content = @Content(examples = @ExampleObject(value = "[{\"id\": \"2882cbdb-d14c-4c72-8f0a-482f9ae46f69\",\"dataDeCriacao\": \"07-12-2024 14:42:18\",\"active\": true,\"nome\": \"Nome Completo\",\"cpf\": \"000.000.000-00\",\"dataDeNascimento\": \"dd-mm-aaaa\",\"contato\": \"00 000000000\"}]"))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(examples = @ExampleObject(value = "[{\"status\":\"BAD_REQUEST\",\n\"message\":\"Não foram encontrados pacientes cadastrados para parâmetro escolhido.\"}]"))),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado", content = @Content(examples = @ExampleObject(value = "[{\"status\":\"NOT_FOUND\",\n\"message\":\"Não foram encontrados pacientes cadastrados para parâmetro escolhido.\"}]"))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(examples = @ExampleObject(value = "[{\"status\":\"INTERNAL_SERVER_ERROR\",\n\"message\":\"Ocorreu um erro inesperado.\"}]")))
    })
    public ResponseEntity<List<Pacientes>> listPacientes(@ModelAttribute Pacientes paciente) {
        pacientesService.listPacientes(paciente);
        return ResponseEntity.ok(pacientesService.listPacientes(paciente));
    }

    @Operation(summary = "Busca um paciente pelo seu ID", description = "Este endpoint retorna um paciente específico com base no ID")
    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente encontrado", content = @Content(examples = @ExampleObject(value = "[{\"id\": \"2882cbdb-d14c-4c72-8f0a-482f9ae46f69\",\"dataDeCriacao\": \"07-12-2024 14:42:18\",\"active\": true,\"nome\": \"Nome Completo\",\"cpf\": \"000.000.000-00\",\"dataDeNascimento\": \"dd-mm-aaaa\",\"contato\": \"00 000000000\"}]"))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(examples = @ExampleObject(value = "[{\"status\":\"BAD_REQUEST\",\n\"message\":\"O valor inserido no campo ID não é um UUID\"}]"))),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado", content = @Content(examples = @ExampleObject(value = "[{\"status\":\"NOT_FOUND\",\n\"message\":\"Não foi encontrado nenhum paciente com o id: <id>\"}]"))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(examples = @ExampleObject(value = "[{\"status\":\"INTERNAL_SERVER_ERROR\",\n\"message\":\"Ocorreu um erro inesperado.\"}]")))
    })
    public ResponseEntity<Pacientes> getPacientesById(@PathVariable UUID id) {
        pacientesService.getByPacientesId(id);
        return ResponseEntity.ok(pacientesService.getByPacientesId(id));
    }


    @Operation(summary = "Cria um novo paciente", description = "Este endpoint criará um novo paciente")
    @PostMapping
    @Transactional
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Paciente cadastrado com sucesso", content = @Content(examples = @ExampleObject(value = "[{\"id\": \"2882cbdb-d14c-4c72-8f0a-482f9ae46f69\",\"dataDeCriacao\": \"07-12-2024 14:42:18\",\"active\": true,\"nome\": \"Nome Completo\",\"cpf\": \"000.000.000-00\",\"dataDeNascimento\": \"dd-mm-aaaa\",\"contato\": \"00 000000000\"}]"))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(examples = @ExampleObject(value = "[{\"status\":\"BAD_REQUEST\",\n\"message\":\"O campo nome não pode ser vazio ou conter números.\"}]"))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(examples = @ExampleObject(value = "[{\"status\":\"INTERNAL_SERVER_ERROR\",\n\"message\":\"Ocorreu um erro inesperado.\"}]")))
    })
    public ResponseEntity<Pacientes> addPacientes(@RequestBody @Valid Pacientes paciente) {
        pacientesService.addPacientes(paciente);
        return ResponseEntity.status(HttpStatus.CREATED).body(pacientesService.addPacientes(paciente));
    }

    @Operation(summary = "Atualiza as informações do paciente", description = "Este endpoint atualiza as informações de um paciente em específico com base no ID")
    @PutMapping("/{id}")
    @Transactional
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cadastro do paciente atualizado", content = @Content(examples = @ExampleObject(value = "[{\"id\": \"2882cbdb-d14c-4c72-8f0a-482f9ae46f69\",\"dataDeCriacao\": \"07-12-2024 14:42:18\",\"active\": true,\"nome\": \"Nome Completo\",\"cpf\": \"000.000.000-00\",\"dataDeNascimento\": \"dd-mm-aaaa\",\"contato\": \"00 000000000\"}]"))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(examples = @ExampleObject(value = "[{\"status\":\"BAD_REQUEST\",\n\"message\":\"Por favor insira um CPF válido.\"}]"))),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado", content = @Content(examples = @ExampleObject(value = "[{\"status\":\"NOT_FOUND\",\n\"message\":\"Não foi encontrado nenhum paciente com o id: <id>\"}]"))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(examples = @ExampleObject(value = "[{\"status\":\"INTERNAL_SERVER_ERROR\",\n\"message\":\"Ocorreu um erro inesperado.\"}]")))
    })
    public ResponseEntity<Pacientes> updatePacientesById(@PathVariable UUID id, @Valid @RequestBody PacientesUpdateDTO pacienteDetails) {
        pacientesService.updatePacientesById(id, pacienteDetails);
        return ResponseEntity.status(HttpStatus.OK).body(pacientesService.updatePacientesById(id, pacienteDetails));
    }

    @Operation(summary = "Inativa o cadastro de um paciente", description = "Este endpoint inativa o cadastro de um paciente em específico com base no ID")
    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cadastro do paciente inativado com sucesso", content = @Content(examples = @ExampleObject(value = "[{\"message\":\"Cadastro desativado com sucesso.\"}]"))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(examples = @ExampleObject(value = "[{\"status\":\"BAD_REQUEST\",\n\"message\":\"O id inserido não é um UUID.\"}]"))),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado", content = @Content(examples = @ExampleObject(value = "[{\"status\":\"NOT_FOUND\",\n\"message\":\"Não foi encontrado nenhum paciente com o id: <id>\"}]"))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(examples = @ExampleObject(value = "[{\"status\":\"INTERNAL_SERVER_ERROR\",\n\"message\":\"Ocorreu um erro inesperado.\"}]")))
    })
    public ResponseEntity<ApiResp> inactivatePacientesById(@PathVariable UUID id) {
        pacientesService.inactivatePacientesById(id);
        return ResponseEntity.ok(new ApiResp("Cadastro desativado com sucesso."));
    }
}