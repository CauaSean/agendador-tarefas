package com.caua.agendadortarefas.business.mapper;

import com.caua.agendadortarefas.business.dto.TarefasDTORecord;
import com.caua.agendadortarefas.infrastructure.entity.TarefasEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TarefasConverter {

    TarefasEntity paraTarefaEntity(TarefasDTORecord dto);
    TarefasDTORecord paraTarefasDTORecord(TarefasEntity entity);

    List<TarefasDTORecord> paraListaTarefasDTORecord(List<TarefasEntity> entities);
}
