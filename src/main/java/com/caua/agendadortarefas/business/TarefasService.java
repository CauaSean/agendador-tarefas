package com.caua.agendadortarefas.business;

import com.caua.agendadortarefas.business.dto.TarefasDTORecord;
import com.caua.agendadortarefas.business.mapper.TarefasConverter;
import com.caua.agendadortarefas.business.mapper.TarefasUpdateConverter;
import com.caua.agendadortarefas.infrastructure.entity.TarefasEntity;
import com.caua.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.caua.agendadortarefas.business.exceptions.ResourceNotFoundException;
import com.caua.agendadortarefas.infrastructure.repository.TarefasRepository;
import com.caua.agendadortarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefasService {

    private final TarefasRepository tarefasRepository;
    private final TarefasConverter tarefasConverter;
    private final JwtUtil jwtUtil;
    private final TarefasUpdateConverter tarefasUpdateConverter;

    public TarefasDTORecord gravarTarefas(String token, TarefasDTORecord dto) {
        // substring(7) para remover o "Bearer " do token
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        // Criando o DTO com os dados automáticos (email do token e datas atuais)
        TarefasDTORecord dtoFinal = new TarefasDTORecord(
                null,
                dto.nomeTarefa(),
                dto.descricao(),
                email,
                LocalDateTime.now(),
                dto.dataEvento(),
                LocalDateTime.now(),
                StatusNotificacaoEnum.PENDENTE
        );

        TarefasEntity entity = tarefasConverter.paraTarefaEntity(dtoFinal);
        return tarefasConverter.paraTarefasDTORecord(tarefasRepository.save(entity));
    }

    public List<TarefasDTORecord> buscarTarefasAgendadasPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal) {
        List<TarefasEntity> lista = tarefasRepository.findBydataEventoBetweenAndStatusNotificacaoEnum(
                dataInicial, dataFinal, StatusNotificacaoEnum.PENDENTE);
        return tarefasConverter.paraListaTarefasDTORecord(lista);
    }

    public List<TarefasDTORecord> buscarTarefasPorEmail(String token) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        return tarefasConverter.paraListaTarefasDTORecord(tarefasRepository.findByEmailUsuario(email));
    }

    public void deletarTarefasPorId(String id) {
        // Verifica se existe antes de deletar para lançar a sua exceção personalizada
        if (!tarefasRepository.existsById(id)) {
            throw new ResourceNotFoundException("Erro ao deletar tarefa: ID inexistente " + id);
        }
        tarefasRepository.deleteById(id);
    }

    public TarefasDTORecord alteraStatus(StatusNotificacaoEnum status, String id) {
        TarefasEntity entity = tarefasRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada: " + id));

        entity.setStatusNotificacaoEnum(status);
        return tarefasConverter.paraTarefasDTORecord(tarefasRepository.save(entity));
    }

    public TarefasDTORecord updateTarefas(TarefasDTORecord dto, String id) {
        TarefasEntity entity = tarefasRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada: " + id));

        tarefasUpdateConverter.updateTarefas(dto, entity);
        // Atualiza a data de alteração se você tiver esse campo na entity
        entity.setDataAlteracao(LocalDateTime.now());

        return tarefasConverter.paraTarefasDTORecord(tarefasRepository.save(entity));
    }
}