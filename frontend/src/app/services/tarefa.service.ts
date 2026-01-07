import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Tarefa, StatusTarefa, TarefaCreate, TarefaUpdate } from '../models/tarefa.model';

/**
 * Serviço para gerenciar operações relacionadas a tarefas.
 *
 * Este serviço fornece métodos para comunicação com a API REST
 * do backend para CRUD de tarefas.
 *
 * @author Stefanini Challenge
 * @version 1.0
 * @since 2026-01-06
 */
@Injectable({
  providedIn: 'root'
})
export class TarefaService {
  private apiUrl = 'http://localhost:8080/api/tarefas';

  constructor(private http: HttpClient) {}

  /**
   * Lista todas as tarefas.
   * @returns Observable com array de tarefas
   */
  listarTodas(): Observable<Tarefa[]> {
    return this.http.get<Tarefa[]>(this.apiUrl);
  }

  /**
   * Lista tarefas filtradas por status.
   * @param status Status das tarefas a serem listadas
   * @returns Observable com array de tarefas
   */
  listarPorStatus(status: StatusTarefa): Observable<Tarefa[]> {
    return this.http.get<Tarefa[]>(`${this.apiUrl}/status/${status}`);
  }

  /**
   * Busca uma tarefa por ID.
   * @param id ID da tarefa
   * @returns Observable com a tarefa encontrada
   */
  buscarPorId(id: number): Observable<Tarefa> {
    return this.http.get<Tarefa>(`${this.apiUrl}/${id}`);
  }

  /**
   * Cria uma nova tarefa.
   * @param tarefa Dados da tarefa a ser criada
   * @returns Observable com a tarefa criada
   */
  criar(tarefa: TarefaCreate): Observable<Tarefa> {
    return this.http.post<Tarefa>(this.apiUrl, tarefa);
  }

  /**
   * Atualiza uma tarefa existente.
   * @param id ID da tarefa a ser atualizada
   * @param tarefa Dados atualizados da tarefa
   * @returns Observable com a tarefa atualizada
   */
  atualizar(id: number, tarefa: TarefaUpdate): Observable<Tarefa> {
    return this.http.put<Tarefa>(`${this.apiUrl}/${id}`, tarefa);
  }

  /**
   * Deleta uma tarefa.
   * @param id ID da tarefa a ser deletada
   * @returns Observable vazio
   */
  deletar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
