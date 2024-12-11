package com.gerenciamentomedico.services;

import com.gerenciamentomedico.dtos.MedicosUpdateDTO;
import com.gerenciamentomedico.entities.users.Medicos;
import com.gerenciamentomedico.exception.exceptions.BadRequestException;
import com.gerenciamentomedico.exception.exceptions.ConflictException;
import com.gerenciamentomedico.exception.exceptions.NotFoundException;
import com.gerenciamentomedico.repository.MedicosRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MedicosService {

    @Autowired
    private MedicosRepository medicosRepository;

    public List<Medicos> listMedicos(@ModelAttribute Medicos medico) {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Medicos> filter = Example.of(medico, matcher);

        List<Medicos> medicosList = medicosRepository.findAll(filter);
        if (medicosList.isEmpty()) {
            throw new NotFoundException("Não foram encontrados pacientes cadastrados para parâmetro escolhido.");
        }
        return medicosRepository.findAll(filter);
    }

    public Medicos getMedicosById(@PathVariable UUID id) {
        return medicosRepository.findById(id).orElseThrow(() -> new NotFoundException("Médico não encontrado com ID: " + id));
    }

    public Medicos addMedicos(@RequestBody @Valid Medicos medico) {
        return medicosRepository.save(medico);
    }

    public Medicos updateMedicosById(@PathVariable UUID id, @RequestBody @Valid MedicosUpdateDTO medicoDetails) {
        Medicos medicoUpdated = medicosRepository.findById(id).orElseThrow(() -> new NotFoundException("Não foi encontrado nenhum médico com o id " + id));

        if (medicoDetails.nome() != null && !medicoDetails.nome().isEmpty()) {
            medicoUpdated.setNome(medicoDetails.nome());
        }
        if (medicoDetails.email() != null && !medicoDetails.email().isEmpty() && !medicoDetails.email().equals(medicoUpdated.getEmail())) {
            boolean emailExiste = medicosRepository.existsByEmailAndIdNot(medicoDetails.crm(), id);
            if (emailExiste) {
                throw new ConflictException("Já existe um médico com esse e-mail cadastrado.");
            }
            medicoUpdated.setEmail(medicoDetails.email());
        }
        if (medicoDetails.crm() != null && !medicoDetails.crm().isEmpty() && !medicoDetails.crm().equals(medicoUpdated.getCrm())) {
            boolean crmExiste = medicosRepository.existsByCrmAndIdNot(medicoDetails.crm(), id);
            if (crmExiste) {
                throw new ConflictException("Já existe um médico com esse CRM cadastrado.");
            }
            medicoUpdated.setCrm(medicoDetails.crm());
        }
        if (medicoDetails.especialidade() != null && !medicoDetails.especialidade().isEmpty()) {
            medicoUpdated.setEspecialidade(medicoDetails.especialidade());
        }

        return medicosRepository.saveAndFlush(medicoUpdated);
    }

    public void inactivateMedicosById(@PathVariable UUID id) {
        Optional<Medicos> medicoInac = medicosRepository.findById(id);
        if (medicoInac.isEmpty()) {
            throw new NotFoundException("O cadastro não foi encontrado");
        }
        if (!medicoInac.get().getActive()) {
            throw new BadRequestException("O cadastro já foi desativado.");
        }
        medicosRepository.deleteById(id);
    }
}
