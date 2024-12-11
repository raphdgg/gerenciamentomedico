package com.gerenciamentomedico.services;

import com.gerenciamentomedico.dtos.PacientesUpdateDTO;
import com.gerenciamentomedico.entities.users.Pacientes;
import com.gerenciamentomedico.exception.exceptions.BadRequestException;
import com.gerenciamentomedico.exception.exceptions.ConflictException;
import com.gerenciamentomedico.exception.exceptions.NotFoundException;
import com.gerenciamentomedico.repository.PacientesRepository;
import com.gerenciamentomedico.utils.CPFUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PacientesService {

    @Autowired
    private PacientesRepository pacientesRepository;

    public List<Pacientes> listPacientes(Pacientes paciente) {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Pacientes> filter = Example.of(paciente, matcher);

        List<Pacientes> pacientesList = pacientesRepository.findAll(filter);
        if (pacientesList.isEmpty()) {
            throw new NotFoundException("Não foram encontrados pacientes cadastrados para parâmetro escolhido.");
        }

        return pacientesRepository.findAll(filter);
    }

    public Pacientes getByPacientesId(UUID id) {
        return pacientesRepository.findById(id).orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + id));
    }

    public Pacientes addPacientes(Pacientes paciente) {
        String cpfLimpo = paciente.getCpf().replaceAll("[^0-9]", "");
        String contatoLimpo = paciente.getContato().replaceAll("[^0-9]", "");
        paciente.setCpf(cpfLimpo);
        paciente.setContato(contatoLimpo);

        return pacientesRepository.save(paciente);
    }

    @Transactional
    public Pacientes updatePacientesById(UUID id, PacientesUpdateDTO pacienteDetails) {
        Pacientes paciente = pacientesRepository.findById(id).orElseThrow(() -> new NotFoundException("Não foi encontrado nenhum paciente com o id: " + id));

        if (pacienteDetails.nome() != null && !pacienteDetails.nome().isEmpty()) {
            paciente.setNome(pacienteDetails.nome());
        }
        if (pacienteDetails.cpf() != null && !pacienteDetails.cpf().isEmpty()) {
            String cpfLimpo = pacienteDetails.cpf().replaceAll("[^0-9]", "");

            if (!CPFUtils.isValidCPF(cpfLimpo)) {
                throw new BadRequestException("Por favor insira um CPF válido.");
            }

            boolean cpfExiste = pacientesRepository.existsByCpfAndIdNot(pacienteDetails.cpf(), id);
            if (cpfExiste) {
                throw new ConflictException("Já existe um paciente com esse CPF cadastrado.");
            }

            paciente.setCpf(cpfLimpo);
        }
        if (pacienteDetails.dataDeNascimento() != null) {
            paciente.setDataDeNascimento(pacienteDetails.dataDeNascimento());

        }
        if (pacienteDetails.contato() != null && !pacienteDetails.contato().isEmpty()) {
            String contatoLimpo = pacienteDetails.contato().replaceAll("[^0-9]", "");
            boolean contatoExiste = pacientesRepository.existsByContatoAndIdNot(contatoLimpo, id);
            if (contatoExiste) {
                throw new ConflictException("Já existe um paciente com esse número de contato cadastrado.");
            }
            paciente.setContato(contatoLimpo);
        }

        return pacientesRepository.save(paciente);
    }

    public void inactivatePacientesById(UUID id) {
        Optional<Pacientes> pacienteInac = pacientesRepository.findById(id);
        if (pacienteInac.isEmpty()) {
            throw new NotFoundException("O cadastro não foi encontrado");
        }
        if (!pacienteInac.get().getActive()) {
            throw new BadRequestException("O cadastro já foi desativado.");
        }
        pacientesRepository.deleteById(id);
    }


}
