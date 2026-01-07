package com.stefanini.todolist.services;

import com.stefanini.todolist.application.dtos.TarefaCreateDTO;
import com.stefanini.todolist.application.dtos.TarefaResponseDTO;
import com.stefanini.todolist.application.dtos.TarefaUpdateDTO;
import com.stefanini.todolist.application.services.TarefaServiceImpl;
import com.stefanini.todolist.domain.entities.Tarefa;
import com.stefanini.todolist.domain.enums.StatusTarefa;
import com.stefanini.todolist.domain.repositories.TarefaRepository;
import com.stefanini.todolist.infrastructure.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para a classe TarefaServiceImpl.
 *
 * @author Stefanini Challenge
 * @version 1.0
 * @since 2026-01-06
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do TarefaService")
class TarefaServiceImplTest {

    @Mock
    private TarefaRepository tarefaRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TarefaServiceImpl tarefaService;

    private Tarefa tarefa;
    private TarefaCreateDTO createDTO;
    private TarefaUpdateDTO updateDTO;
    private TarefaResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        tarefa = Tarefa.builder()
                .id(1L)
                .titulo("Teste")
                .descricao("Descrição de teste")
                .status(StatusTarefa.PENDENTE)
                .dataCriacao(LocalDateTime.now())
                .build();

        createDTO = TarefaCreateDTO.builder()
                .titulo("Teste")
                .descricao("Descrição de teste")
                .status(StatusTarefa.PENDENTE)
                .build();

        updateDTO = TarefaUpdateDTO.builder()
                .titulo("Teste Atualizado")
                .descricao("Descrição atualizada")
                .status(StatusTarefa.EM_ANDAMENTO)
                .build();

        responseDTO = TarefaResponseDTO.builder()
                .id(1L)
                .titulo("Teste")
                .descricao("Descrição de teste")
                .status(StatusTarefa.PENDENTE)
                .dataCriacao(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("Deve criar uma tarefa com sucesso")
    void deveCriarTarefaComSucesso() {
        when(modelMapper.map(createDTO, Tarefa.class)).thenReturn(tarefa);
        when(tarefaRepository.save(any(Tarefa.class))).thenReturn(tarefa);
        when(modelMapper.map(tarefa, TarefaResponseDTO.class)).thenReturn(responseDTO);

        TarefaResponseDTO resultado = tarefaService.criar(createDTO);

        assertNotNull(resultado);
        assertEquals(responseDTO.getTitulo(), resultado.getTitulo());
        verify(tarefaRepository, times(1)).save(any(Tarefa.class));
    }

    @Test
    @DisplayName("Deve buscar tarefa por ID com sucesso")
    void deveBuscarTarefaPorIdComSucesso() {
        when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefa));
        when(modelMapper.map(tarefa, TarefaResponseDTO.class)).thenReturn(responseDTO);

        TarefaResponseDTO resultado = tarefaService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(tarefaRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção quando tarefa não for encontrada")
    void deveLancarExcecaoQuandoTarefaNaoForEncontrada() {
        when(tarefaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            tarefaService.buscarPorId(99L);
        });

        verify(tarefaRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Deve listar todas as tarefas")
    void deveListarTodasAsTarefas() {
        List<Tarefa> tarefas = Arrays.asList(tarefa);
        when(tarefaRepository.findAllOrderByDataCriacaoDesc()).thenReturn(tarefas);
        when(modelMapper.map(any(Tarefa.class), eq(TarefaResponseDTO.class))).thenReturn(responseDTO);

        List<TarefaResponseDTO> resultado = tarefaService.listarTodas();

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(tarefaRepository, times(1)).findAllOrderByDataCriacaoDesc();
    }

    @Test
    @DisplayName("Deve atualizar tarefa com sucesso")
    void deveAtualizarTarefaComSucesso() {
        when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefa));
        when(tarefaRepository.save(any(Tarefa.class))).thenReturn(tarefa);
        when(modelMapper.map(tarefa, TarefaResponseDTO.class)).thenReturn(responseDTO);

        TarefaResponseDTO resultado = tarefaService.atualizar(1L, updateDTO);

        assertNotNull(resultado);
        verify(tarefaRepository, times(1)).findById(1L);
        verify(tarefaRepository, times(1)).save(any(Tarefa.class));
    }

    @Test
    @DisplayName("Deve deletar tarefa com sucesso")
    void deveDeletarTarefaComSucesso() {
        when(tarefaRepository.existsById(1L)).thenReturn(true);
        doNothing().when(tarefaRepository).deleteById(1L);

        assertDoesNotThrow(() -> tarefaService.deletar(1L));

        verify(tarefaRepository, times(1)).existsById(1L);
        verify(tarefaRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Deve listar tarefas por status")
    void deveListarTarefasPorStatus() {
        List<Tarefa> tarefas = Arrays.asList(tarefa);
        when(tarefaRepository.findByStatus(StatusTarefa.PENDENTE)).thenReturn(tarefas);
        when(modelMapper.map(any(Tarefa.class), eq(TarefaResponseDTO.class))).thenReturn(responseDTO);

        List<TarefaResponseDTO> resultado = tarefaService.listarPorStatus(StatusTarefa.PENDENTE);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(tarefaRepository, times(1)).findByStatus(StatusTarefa.PENDENTE);
    }
}

