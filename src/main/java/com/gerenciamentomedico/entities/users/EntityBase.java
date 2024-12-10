package com.gerenciamentomedico.entities.users;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)

public abstract class EntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, updatable = false)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "America/Sao_Paulo")
    private LocalDateTime dataDeCriacao;

    @PrePersist
    public void prePersist() {
        this.dataDeCriacao = LocalDateTime.now();
    }


}
