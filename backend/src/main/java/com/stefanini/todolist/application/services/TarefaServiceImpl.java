package com.stefanini.todolist.application.services;

import com.stefanini.todolist.application.dtos.TarefaCreateDTO;
import com.stefanini.todolist.application.dtos.TarefaResponseDTO;
import com.stefanini.todolist.application.dtos.TarefaUpdateDTO;
import com.stefanini.todolist.domain.entities.Tarefa;
import com.stefanini.todolist.domain.enums.StatusTarefa;
import com.stefanini.todolist.domain.repositories.TarefaRepository;
import com.stefanini.todolist.infrastructure.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementação do serviço de Tarefas.
 *
 * <p>Esta classe implementa a lógica de negócio para gerenciamento de tarefas,
 * incluindo validações, transformações de dados e coordenação com o repositório.</p>
 *
 * <p>Utiliza o padrão de Service Layer e segue os princípios SOLID:
 * <ul>
 *   <li>Single Responsibility: Responsável apenas pela lógica de negócio de tarefas</li>
 *   <li>Open/Closed: Extensível através da interface TarefaService</li>
 *   <li>Liskov Substitution: Pode ser substituída por qualquer implementação da interface</li>
 *   <li>Interface Segregation: Interface específica para operações de tarefas</li>
 *   <li>Dependency Inversion: Depende de abstrações (Repository interface)</li>
 * </ul>
 * </p>
 *
 * @author Stefanini Challenge
 * @version 1.0
 * @since 2026-01-06
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class TarefaServiceImpl implements TarefaService {

    private final TarefaRepository tarefaRepository;
    private final ModelMapper modelMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public TarefaResponseDTO criar(TarefaCreateDTO createDTO) {
        log.info("Criando nova tarefa com título: {}", createDTO.getTitulo());

        Tarefa tarefa = modelMapper.map(createDTO, Tarefa.class);

        // Se o status não foi informado, define como PENDENTE
        if (tarefa.getStatus() == null) {
            tarefa.setStatus(StatusTarefa.PENDENTE);
        }

        Tarefa tarefaSalva = tarefaRepository.save(tarefa);
        log.info("Tarefa criada com sucesso. ID: {}", tarefaSalva.getId());

        return modelMapper.map(tarefaSalva, TarefaResponseDTO.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TarefaResponseDTO buscarPorId(Long id) {
        log.info("Buscando tarefa por ID: {}", id);

        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Tarefa não encontrada com ID: {}", id);
                    return new ResourceNotFoundException("Tarefa não encontrada com ID: " + id);
                });

        return modelMapper.map(tarefa, TarefaResponseDTO.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TarefaResponseDTO> listarTodas() {
        log.info("Listando todas as tarefas");

        List<Tarefa> tarefas = tarefaRepository.findAllOrderByDataCriacaoDesc();
        log.info("Total de tarefas encontradas: {}", tarefas.size());

        return tarefas.stream()
                .map(tarefa -> modelMapper.map(tarefa, TarefaResponseDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TarefaResponseDTO> listarPorStatus(StatusTarefa status) {
        log.info("Listando tarefas por status: {}", status);

        List<Tarefa> tarefas = tarefaRepository.findByStatus(status);
        log.info("Total de tarefas encontradas com status {}: {}", status, tarefas.size());

        return tarefas.stream()
                .map(tarefa -> modelMapper.map(tarefa, TarefaResponseDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public TarefaResponseDTO atualizar(Long id, TarefaUpdateDTO updateDTO) {
        log.info("Atualizando tarefa com ID: {}", id);

        Tarefa tarefaExistente = tarefaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Tarefa não encontrada com ID: {}", id);
                    return new ResourceNotFoundException("Tarefa não encontrada com ID: " + id);
                });

        // Atualiza os campos
        tarefaExistente.setTitulo(updateDTO.getTitulo());
        tarefaExistente.setDescricao(updateDTO.getDescricao());

        if (updateDTO.getStatus() != null) {
            tarefaExistente.setStatus(updateDTO.getStatus());
        }

        Tarefa tarefaAtualizada = tarefaRepository.save(tarefaExistente);
        log.info("Tarefa atualizada com sucesso. ID: {}", tarefaAtualizada.getId());

        return modelMapper.map(tarefaAtualizada, TarefaResponseDTO.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deletar(Long id) {
        log.info("Deletando tarefa com ID: {}", id);

        if (!tarefaRepository.existsById(id)) {
            log.warn("Tarefa não encontrada com ID: {}", id);
            throw new ResourceNotFoundException("Tarefa não encontrada com ID: " + id);
        }

        tarefaRepository.deleteById(id);
        log.info("Tarefa deletada com sucesso. ID: {}", id);
    }
}

