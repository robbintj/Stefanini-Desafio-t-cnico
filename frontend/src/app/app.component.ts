import { Component, OnInit, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TarefaService } from './services/tarefa.service';
import { Tarefa, StatusTarefa, TarefaCreate, TarefaUpdate } from './models/tarefa.model';

/**
 * Componente principal da aplicação de gerenciamento de tarefas.
 *
 * Este componente gerencia a interface do usuário para criar, listar,
 * editar e excluir tarefas, integrando com a API backend.
 *
 * @author Stefanini Challenge
 * @version 1.0
 * @since 2026-01-06
 */
@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'Sistema de Gerenciamento de Tarefas';

  // Signals
  tarefas = signal<Tarefa[]>([]);
  filtroStatus = signal<string>('TODAS');
  tarefaEditando = signal<Tarefa | null>(null);
  mensagemSucesso = signal<string>('');
  mensagemErro = signal<string>('');

  // Computed
  modoEdicao = computed(() => this.tarefaEditando() !== null);
  tarefaAtual = computed(() => this.tarefaEditando() || this.novaTarefa);

  // Formulário de tarefa
  novaTarefa: TarefaCreate = {
    titulo: '',
    descricao: '',
    status: StatusTarefa.PENDENTE
  };

  // Enum para template
  StatusTarefa = StatusTarefa;
  statusOptions = Object.values(StatusTarefa);

  constructor(private tarefaService: TarefaService) {}

  ngOnInit(): void {
    this.carregarTarefas();
  }

  /**
   * Carrega todas as tarefas ou filtra por status.
   */
  carregarTarefas(): void {
    if (this.filtroStatus() === 'TODAS') {
      this.tarefaService.listarTodas().subscribe({
        next: (tarefas) => {
          this.tarefas.set(tarefas);
          this.limparMensagens();
        },
        error: (erro) => this.exibirErro('Erro ao carregar tarefas: ' + erro.message)
      });
    } else {
      this.tarefaService.listarPorStatus(this.filtroStatus() as StatusTarefa).subscribe({
        next: (tarefas) => {
          this.tarefas.set(tarefas);
          this.limparMensagens();
        },
        error: (erro) => this.exibirErro('Erro ao filtrar tarefas: ' + erro.message)
      });
    }
  }

  /**
   * Cria uma nova tarefa.
   */
  criarTarefa(): void {
    if (!this.validarFormulario()) {
      return;
    }

    this.tarefaService.criar(this.novaTarefa).subscribe({
      next: (tarefa) => {
        this.exibirSucesso('Tarefa criada com sucesso!');
        this.limparFormulario();
        this.carregarTarefas();
      },
      error: (erro) => this.exibirErro('Erro ao criar tarefa: ' + erro.message)
    });
  }

  /**
   * Prepara a tarefa para edição.
   */
  editarTarefa(tarefa: Tarefa): void {
    this.tarefaEditando.set({ ...tarefa });
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  /**
   * Atualiza a tarefa editada.
   */
  atualizarTarefa(): void {
    const tarefa = this.tarefaEditando();
    if (!tarefa || !tarefa.id) {
      return;
    }

    const tarefaUpdate: TarefaUpdate = {
      titulo: tarefa.titulo,
      descricao: tarefa.descricao,
      status: tarefa.status
    };

    this.tarefaService.atualizar(tarefa.id, tarefaUpdate).subscribe({
      next: (tarefa) => {
        this.exibirSucesso('Tarefa atualizada com sucesso!');
        this.cancelarEdicao();
        this.carregarTarefas();
      },
      error: (erro) => this.exibirErro('Erro ao atualizar tarefa: ' + erro.message)
    });
  }
  /**
   * Cancela a edição e limpa o formulário.
   */
  cancelarEdicao(): void {
    this.tarefaEditando.set(null);
    this.limparFormulario();
  }

  /**
   * Deleta uma tarefa.
   */
  deletarTarefa(id: number): void {
    if (!confirm('Tem certeza que deseja excluir esta tarefa?')) {
      return;
    }

    this.tarefaService.deletar(id).subscribe({
      next: () => {
        this.exibirSucesso('Tarefa excluída com sucesso!');
        this.carregarTarefas();
      },
      error: (erro) => this.exibirErro('Erro ao excluir tarefa: ' + erro.message)
    });
  }

  /**
   * Valida o formulário.
   */
  validarFormulario(): boolean {
    if (!this.novaTarefa.titulo || this.novaTarefa.titulo.trim().length < 3) {
      this.exibirErro('O título deve ter no mínimo 3 caracteres');
      return false;
    }
    return true;
  }

  /**
   * Limpa o formulário.
   */
  limparFormulario(): void {
    this.novaTarefa = {
      titulo: '',
      descricao: '',
      status: StatusTarefa.PENDENTE
    };
  }

  /**
   * Retorna a classe CSS para o badge de status.
   */
  getStatusClass(status: StatusTarefa): string {
    const classes: { [key in StatusTarefa]: string } = {
      [StatusTarefa.PENDENTE]: 'badge-warning',
      [StatusTarefa.EM_ANDAMENTO]: 'badge-info',
      [StatusTarefa.CONCLUIDA]: 'badge-success'
    };
    return classes[status];
  }

  /**
   * Retorna o texto formatado do status.
   */
  getStatusTexto(status: StatusTarefa): string {
    const textos: { [key in StatusTarefa]: string } = {
      [StatusTarefa.PENDENTE]: 'Pendente',
      [StatusTarefa.EM_ANDAMENTO]: 'Em Andamento',
      [StatusTarefa.CONCLUIDA]: 'Concluída'
    };
    return textos[status];
  }

  /**
   * Exibe mensagem de sucesso.
   */
  exibirSucesso(mensagem: string): void {
    this.mensagemSucesso.set(mensagem);
    this.mensagemErro.set('');
    setTimeout(() => this.mensagemSucesso.set(''), 5000);
  }

  /**
   * Exibe mensagem de erro.
   */
  exibirErro(mensagem: string): void {
    this.mensagemErro.set(mensagem);
    this.mensagemSucesso.set('');
    setTimeout(() => this.mensagemErro.set(''), 5000);
  }

  /**
   * Limpa todas as mensagens.
   */
  limparMensagens(): void {
    this.mensagemSucesso.set('');
    this.mensagemErro.set('');
  }
}
