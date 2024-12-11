package com.gerenciamentomedico.services;

import com.gerenciamentomedico.dtos.ConsultasUpdateDTO;
import com.gerenciamentomedico.entities.consultas.Consultas;
import com.gerenciamentomedico.entities.users.Medicos;
import com.gerenciamentomedico.entities.users.Pacientes;
import com.gerenciamentomedico.exception.exceptions.BadRequestException;
import com.gerenciamentomedico.exception.exceptions.ConflictException;
import com.gerenciamentomedico.exception.exceptions.NotFoundException;
import com.gerenciamentomedico.repository.ConsultasRepository;
import com.gerenciamentomedico.repository.MedicosRepository;
import com.gerenciamentomedico.repository.PacientesRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ConsultasService {

    @Autowired
    private ConsultasRepository consultasRepository;
    @Autowired
    private PacientesRepository pacientesRepository;
    @Autowired
    private MedicosRepository medicosRepository;

    public List<Consultas> listConsultas(Consultas consulta) {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Consultas> filter = Example.of(consulta, matcher);

        List<Consultas> consultasList = consultasRepository.findAll(filter);
        if (consultasList.isEmpty()) {
            throw new NotFoundException("Não foram encontradas consultas agendadas para parâmetro escolhido.");
        }

        return consultasRepository.findAll(filter);
    }

    public Consultas getConsultasById(UUID id) {
        return consultasRepository.findById(id).orElseThrow(() -> new NotFoundException("Consulta não encontrada com o id: " + id));
    }

    public Consultas addConsultas(Consultas consulta) {

        Optional<Consultas> consultaPacienteExistente = consultasRepository.findByPacienteIdAndDataHoraConsultaBetween(
                consulta.getPacienteId(),
                consulta.getDataHoraConsulta().minusMinutes(14),
                consulta.getDataHoraConsulta().plusMinutes(14)
        );
        if (consultaPacienteExistente.isPresent() && !consultaPacienteExistente.get().getId().equals(consulta.getId())) {
            throw new ConflictException("O paciente já possui uma consulta agendada no horário ou dentro de um intervalo de 15 minutos.");
        }
        Optional<Consultas> consultaMedicoExistente = consultasRepository.findByMedicoIdAndDataHoraConsultaBetween(
                consulta.getMedicoId(),
                consulta.getDataHoraConsulta().minusMinutes(14),
                consulta.getDataHoraConsulta().plusMinutes(14)
        );
        if (consultaMedicoExistente.isPresent() && !consultaMedicoExistente.get().getId().equals(consulta.getId())) {
            throw new ConflictException("O médico já possui uma consulta agendada no horário ou dentro de um intervalo de 15 minutos.");
        }
        if (consulta.getPacienteId() != null) {
            Optional<Pacientes> pacienteExistente = pacientesRepository.findById(consulta.getPacienteId());
            if (pacienteExistente.isEmpty()) {
                throw new NotFoundException("O paciente informado não existe.");
            }
        }
        if (consulta.getMedicoId() != null) {
            Optional<Medicos> medicoExistente = medicosRepository.findById(consulta.getMedicoId());
            if (medicoExistente.isEmpty()) {
                throw new NotFoundException("O médico informado não existe.");
            }
        }

        return consultasRepository.save(consulta);
    }

    @Transactional
    public Consultas updateConsultasById(UUID id, ConsultasUpdateDTO consultaDetails) {
        Consultas consulta = consultasRepository.findById(id).orElseThrow(() -> new NotFoundException("Não foi encontrado nenhuma consulta com o id: " + id));

        if (consultaDetails.pacienteId() != null && !consultaDetails.pacienteId().equals(consulta.getPacienteId())) {
            Optional<Pacientes> paciente = pacientesRepository.findById(consultaDetails.pacienteId());
            if (paciente.isEmpty()) {
                throw new NotFoundException("Paciente com o id " + consultaDetails.pacienteId() + " não encontrado.");
            }
            consulta.setPacienteId(consultaDetails.pacienteId());
        }
        if (consultaDetails.medicoId() != null && !consultaDetails.medicoId().equals(consulta.getMedicoId())) {
            Optional<Medicos> medico = medicosRepository.findById(consultaDetails.medicoId());
            if (medico.isEmpty()) {
                throw new NotFoundException("Médico com o id " + consultaDetails.medicoId() + " não encontrado.");
            }
            consulta.setMedicoId(consultaDetails.medicoId());
        }

        return consultasRepository.saveAndFlush(consulta);
    }

    @Transactional
    public void concludeConsultasById(UUID id) {
        Consultas consulta = consultasRepository.findById(id).orElseThrow(() -> new NotFoundException("Consulta não encontrada com o id: " + id));

        if (consulta.getStatusDaConsulta() == Consultas.Status.CONCLUIDA) {
            throw new NotFoundException("A consulta já foi concluída.");
        }
        if (consulta.getStatusDaConsulta() == Consultas.Status.CANCELADA) {
            throw new BadRequestException("A consulta foi cancelada e não pode ser concluída.");
        }
        consulta.setStatusDaConsulta(Consultas.Status.CONCLUIDA);

        consultasRepository.saveAndFlush(consulta);
    }

    public void cancelConsultasById(@PathVariable UUID id) {
        Optional<Consultas> consultaDel = consultasRepository.findById(id);
        if (consultaDel.isEmpty()) {
            throw new NotFoundException("Não foi encontrada consulta com o id " + id);
        }
        if (consultaDel.get().getStatusDaConsulta() == Consultas.Status.CANCELADA) {
            throw new NotFoundException("A consulta já foi cancelada.");
        }
        if (consultaDel.get().getStatusDaConsulta() == Consultas.Status.CONCLUIDA) {
            throw new BadRequestException("A consulta já foi concluída e não pode ser cancelada.");
        }
        consultasRepository.deleteById(id);
    }
}
