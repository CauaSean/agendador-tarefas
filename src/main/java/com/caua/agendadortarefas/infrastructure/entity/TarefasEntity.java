package com.caua.agendadortarefas.infrastructure.entity;

import com.caua.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tarefa")
public class TarefasEntity {

    @Id
    private String id;

    private String nomeTarefa;
    private String descricao;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataEvento;
    private String emailUsuario;
    private LocalDateTime dataAlteracao;

    @Enumerated(EnumType.STRING)
    private StatusNotificacaoEnum statusNotificacaoEnum;

}
