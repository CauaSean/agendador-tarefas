package com.caua.agendadortarefas.business.mapper;

import com.caua.agendadortarefas.business.dto.TarefasDTO;
import com.caua.agendadortarefas.infrastructure.entity.TarefasEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TarefasConverter {

    TarefasEntity paraTarefasEntity(TarefasDTO dto);

    TarefasDTO paraTarefasDTO(TarefasEntity entity);
}
