package com.gerenciamentomedico.repository;

import com.gerenciamentomedico.entities.consultas.Consultas;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface ConsultasRepository extends JpaRepository<Consultas, UUID> {

    void deleteById(UUID id);

    Optional<Consultas> findByPacienteIdAndDataHoraConsultaBetween(@NotNull(message = "O campo Paciente ID não pode estar vazio.") UUID pacienteId, LocalDateTime localDateTime, LocalDateTime localDateTime1);

    Optional<Consultas> findByMedicoIdAndDataHoraConsultaBetween(@NotNull(message = "O campo Medico ID não pode estar vazio.") UUID medicoId, LocalDateTime localDateTime, LocalDateTime localDateTime1);
}
