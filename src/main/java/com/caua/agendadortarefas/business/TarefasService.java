package com.caua.agendadortarefas.business;

import com.caua.agendadortarefas.business.dto.TarefasDTO;
import com.caua.agendadortarefas.business.mapper.TarefasConverter;
import com.caua.agendadortarefas.infrastructure.entity.TarefasEntity;
import com.caua.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.caua.agendadortarefas.infrastructure.repository.TarefasRepository;
import com.caua.agendadortarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TarefasService {

    private final TarefasRepository tarefasRepository;
    private final TarefasConverter tarefasConverter;
    private final JwtUtil jwtUtil;

    public TarefasDTO gravarTarefas(String token, TarefasDTO dto){
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        dto.setEmailUsuario(email);

        dto.setDataCriacao(LocalDateTime.now());
        dto.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);
        TarefasEntity entity = tarefasConverter.paraTarefasEntity(dto);

        return tarefasConverter.paraTarefasDTO(
                tarefasRepository.save(entity));
    }
}
