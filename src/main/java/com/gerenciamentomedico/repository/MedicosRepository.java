package com.gerenciamentomedico.repository;

import com.gerenciamentomedico.entities.users.Medicos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MedicosRepository extends JpaRepository<Medicos, UUID> {

    void deleteById(UUID id);

    Optional<Medicos> findById(UUID id);

    boolean existsByCrmAndIdNot(String crm, UUID id);

    boolean existsByEmailAndIdNot(String email, UUID id);
}
