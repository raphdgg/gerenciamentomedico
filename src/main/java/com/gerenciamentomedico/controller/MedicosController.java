package com.gerenciamentomedico.controller;

import com.gerenciamentomedico.dtos.MedicosUpdateDTO;
import com.gerenciamentomedico.entities.users.Medicos;
import com.gerenciamentomedico.services.MedicosService;
import com.gerenciamentomedico.utils.ApiResp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cadastro do médico atualizado", content = @Content(examples = @ExampleObject(value = "[{\"id\":\"ba362111-3292-4866-849b-fde163dd399d\",\n\"dataDeCriacao\":\"05-12-2024 08:47:39\",\n\"active\": true\",\n\"nome\": \"Nome Completo\",\n\"especialidade\": \"Especialidade\",\n\"crm\": \"000000-UF\",\n\"email\": \"example@email.com\"}]"))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(examples = @ExampleObject(value = "[{\"status\":\"BAD_REQUEST\",\n\"message\":\"Não foram encontrados médicos cadastrados para parâmetro escolhido.\"}]"))),
            @ApiResponse(responseCode = "404", description = "Médico não encontrado", content = @Content(examples = @ExampleObject(value = "[{\"status\":\"NOT_FOUND\",\n\"message\":\"Não foram encontrados médicos cadastrados para parâmetro escolhido.\"}]"))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(examples = @ExampleObject(value = "[{\"status\":\"INTERNAL_SERVER_ERROR\",\n\"message\":\"Ocorreu um erro inesperado.\"}]")))
    })
    public ResponseEntity<List<Medicos>> list(@ModelAttribute Medicos medico) {
        medicosService.listMedicos(medico);
        return ResponseEntity.ok(medicosService.listMedicos(medico));
    }

    @Operation(summary = "Busca um médico pelo seu ID", description = "Este endpoint retorna um médico específico com base no ID")
    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cadastro do médico atualizado", content = @Content(examples = @ExampleObject(value = "[{\"id\":\"ba362111-3292-4866-849b-fde163dd399d\",\n\"dataDeCriacao\":\"05-12-2024 08:47:39\",\n\"active\": true\",\n\"nome\": \"Nome Completo\",\n\"especialidade\": \"Especialidade\",\n\"crm\": \"000000-UF\",\n\"email\": \"example@email.com\"}]"))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(examples = @ExampleObject(value = "[{\"status\":\"BAD_REQUEST\",\n\"message\":\"O valor inserido no campo ID não é um UUID\"}]"))),
            @ApiResponse(responseCode = "404", description = "Médico não encontrado", content = @Content(examples = @ExampleObject(value = "[{\"status\":\"NOT_FOUND\",\n\"message\":\"Não foi encontrado nenhum médico com o id: <id>\"}]"))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(examples = @ExampleObject(value = "[{\"status\":\"INTERNAL_SERVER_ERROR\",\n\"message\":\"Ocorreu um erro inesperado.\"}]")))
    })
    public ResponseEntity<Medicos> getMedicosById(@PathVariable UUID id) {
        medicosService.getMedicosById(id);
        return ResponseEntity.ok(medicosService.getMedicosById(id));
    }

    @Operation(summary = "Cria um novo médico", description = "Este endpoint criará um novo médico")
    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cadastro do médico atualizado", content = @Content(examples = @ExampleObject(value = "[{\"id\":\"ba362111-3292-4866-849b-fde163dd399d\",\n\"dataDeCriacao\":\"05-12-2024 08:47:39\",\n\"active\": true\",\n\"nome\": \"Nome Completo\",\n\"especialidade\": \"Especialidade\",\n\"crm\": \"000000-UF\",\n\"email\": \"example@email.com\"}]"))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(examples = @ExampleObject(value = "[{\"status\":\"BAD_REQUEST\",\n\"message\":\"O campo nome não pode ser vazio ou conter números.\"}]"))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(examples = @ExampleObject(value = "[{\"status\":\"INTERNAL_SERVER_ERROR\",\n\"message\":\"Ocorreu um erro inesperado.\"}]")))
    })
    public ResponseEntity<Medicos> addMedicos(@RequestBody @Valid Medicos medico) {
        medicosService.addMedicos(medico);
        return ResponseEntity.ok(medicosService.addMedicos(medico));
    }

    @Operation(summary = "Atualiza as informações do médico", description = "Este endpoint atualiza as informações de um médico em específico com base no ID")
    @PutMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cadastro do médico atualizado", content = @Content(examples = @ExampleObject(value = "[{\"id\":\"ba362111-3292-4866-849b-fde163dd399d\",\n\"dataDeCriacao\":\"05-12-2024 08:47:39\",\n\"active\": true\",\n\"nome\": \"Nome Completo\",\n\"especialidade\": \"Especialidade\",\n\"crm\": \"000000-UF\",\n\"email\": \"example@email.com\"}]"))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(examples = @ExampleObject(value = "[{\"status\":\"BAD_REQUEST\",\n\"message\":\"O CRM deve seguir o formato '000000-UF', com 4 a 6 dígitos seguidos por um hífen e a sigla do estado em letras maiúsculas.\"}]"))),
            @ApiResponse(responseCode = "404", description = "Médico não encontrado", content = @Content(examples = @ExampleObject(value = "[{\"status\":\"NOT_FOUND\",\n\"message\":\"Não foi encontrado nenhum médico com o id: <id>\"}]"))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(examples = @ExampleObject(value = "[{\"status\":\"INTERNAL_SERVER_ERROR\",\n\"message\":\"Ocorreu um erro inesperado.\"}]")))
    })
    public ResponseEntity<Medicos> updateMedicosById(@PathVariable UUID id, @RequestBody @Valid MedicosUpdateDTO medicoDetails) {
        medicosService.updateMedicosById(id, medicoDetails);
        return ResponseEntity.ok(medicosService.updateMedicosById(id, medicoDetails));
    }

    @Operation(summary = "Inativa o cadastro de um médico", description = "Este endpoint inativa o cadastro de um médico em específico com base no ID")
    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cadastro do médico inativado com sucesso", content = @Content(examples = @ExampleObject(value = "[{\"message\":\"Cadastro desativado com sucesso.\"}]"))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(examples = @ExampleObject(value = "[{\"status\":\"BAD_REQUEST\",\n\"message\":\"O id inserido não é um UUID.\"}]"))),
            @ApiResponse(responseCode = "404", description = "Médico não encontrado", content = @Content(examples = @ExampleObject(value = "[{\"status\":\"NOT_FOUND\",\n\"message\":\"Não foi encontrado nenhum médico com o id: <id>\"}]"))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(examples = @ExampleObject(value = "[{\"status\":\"INTERNAL_SERVER_ERROR\",\n\"message\":\"Ocorreu um erro inesperado.\"}]")))
    })
    public ResponseEntity<ApiResp> inactivateMedicosById(@PathVariable UUID id) {
        medicosService.inactivateMedicosById(id);
        return ResponseEntity.ok(new ApiResp("Cadastro desativado com sucesso."));
    }


}

