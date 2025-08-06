package com.caua.agendadortarefas.business;

import com.caua.agendadortarefas.business.dto.TarefasDTO;
import com.caua.agendadortarefas.infrastructure.repository.TarefasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TarefasService {

    private TarefasRepository tarefasRepository;

    public TarefasDTO gravarTarefa(TarefasDTO dto){

        return tarefasRepository.save()
    }
}
