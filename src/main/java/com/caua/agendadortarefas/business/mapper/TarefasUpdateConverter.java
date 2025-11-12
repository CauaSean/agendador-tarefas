package com.caua.agendadortarefas.business.mapper;

import com.caua.agendadortarefas.business.dto.TarefasDTORecord;
import com.caua.agendadortarefas.infrastructure.entity.TarefasEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TarefasUpdateConverter {

    void  updateTarefas(TarefasDTORecord dto, @MappingTarget TarefasEntity entity);
}
