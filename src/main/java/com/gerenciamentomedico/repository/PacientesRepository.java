package com.gerenciamentomedico.repository;

import com.gerenciamentomedico.entities.users.Pacientes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PacientesRepository extends JpaRepository<Pacientes, UUID> {

    void deleteById(UUID id);

    Optional<Pacientes> findById(UUID id);

    boolean existsByCpfAndIdNot(String cpf, UUID id);

    boolean existsByContatoAndIdNot(String contato, UUID id);
}