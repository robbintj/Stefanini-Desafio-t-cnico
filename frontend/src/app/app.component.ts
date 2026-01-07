import { Component, OnInit, signal, computed, effect } from '@angular/core';
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
  mensagemAviso = signal<string>('');
  mensagemErro = signal<string>('');
  tarefaParaDeletar = signal<number | null>(null);
  private timeoutId: any = null;

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
        },
        error: (erro) => this.exibirErro('Erro ao carregar tarefas: ' + erro.message)
      });
    } else {
      this.tarefaService.listarPorStatus(this.filtroStatus() as StatusTarefa).subscribe({
        next: (tarefas) => {
          this.tarefas.set(tarefas);
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

    console.log('Criando tarefa...', this.novaTarefa);
    this.tarefaService.criar(this.novaTarefa).subscribe({
      next: (tarefa) => {
        console.log('Tarefa criada com sucesso:', tarefa);
        this.exibirSucesso('Tarefa criada com sucesso!');
        console.log('Mensagem de sucesso definida:', this.mensagemSucesso());
        this.limparFormulario();
        this.carregarTarefas();
      },
      error: (erro) => {
        console.error('Erro ao criar tarefa:', erro);
        this.exibirErro('Erro ao criar tarefa: ' + erro.message);
      }
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

    console.log('Atualizando tarefa...', tarefa.id, tarefaUpdate);
    this.tarefaService.atualizar(tarefa.id, tarefaUpdate).subscribe({
      next: (tarefa) => {
        console.log('Tarefa atualizada com sucesso:', tarefa);
        this.exibirAviso('Tarefa atualizada com sucesso!');
        console.log('Mensagem de aviso definida:', this.mensagemAviso());
        this.cancelarEdicao();
        this.carregarTarefas();
      },
      error: (erro) => {
        console.error('Erro ao atualizar tarefa:', erro);
        this.exibirErro('Erro ao atualizar tarefa: ' + erro.message);
      }
    });
  }
  /**
   * Cancela a edição e limpa o formulário.
   */
  cancelarEdicao(): void {
    this.tarefaEditando.set(null);
    this.limparFormularioEdicao();
  }

  /**
   * Limpa apenas o formulário de edição sem afetar mensagens.
   */
  limparFormularioEdicao(): void {
    // Não chama limparFormulario() para não limpar as mensagens de sucesso/erro
  }

  /**
   * Deleta uma tarefa.
   */
  deletarTarefa(id: number): void {
    this.tarefaParaDeletar.set(id);
  }

  /**
   * Confirma a exclusão da tarefa.
   */
  confirmarDelecao(): void {
    const id = this.tarefaParaDeletar();
    if (!id) return;

    this.tarefaService.deletar(id).subscribe({
      next: () => {
        this.exibirSucesso('Tarefa excluída com sucesso!');
        this.tarefaParaDeletar.set(null);
        this.carregarTarefas();
      },
      error: (erro) => {
        this.exibirErro('Erro ao excluir tarefa: ' + erro.message);
        this.tarefaParaDeletar.set(null);
      }
    });
  }

  /**
   * Cancela a exclusão da tarefa.
   */
  cancelarDelecao(): void {
    this.tarefaParaDeletar.set(null);
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
    console.log('exibirSucesso chamado com:', mensagem);
    
    // Limpar timeout anterior se existir
    if (this.timeoutId) {
      clearTimeout(this.timeoutId);
    }
    
    this.mensagemSucesso.set(mensagem);
    this.mensagemAviso.set('');
    this.mensagemErro.set('');
    this.tarefaParaDeletar.set(null);
    
    console.log('Estado após set:', {
      sucesso: this.mensagemSucesso(),
      aviso: this.mensagemAviso(),
      erro: this.mensagemErro(),
      deletar: this.tarefaParaDeletar()
    });
    
    this.timeoutId = setTimeout(() => {
      console.log('Limpando mensagem de sucesso após 8s');
      this.mensagemSucesso.set('');
      this.timeoutId = null;
    }, 8000);
  }

  /**
   * Exibe mensagem de aviso (amarelo).
   */
  exibirAviso(mensagem: string): void {
    console.log('exibirAviso chamado com:', mensagem);
    
    // Limpar timeout anterior se existir
    if (this.timeoutId) {
      clearTimeout(this.timeoutId);
    }
    
    this.mensagemAviso.set(mensagem);
    this.mensagemSucesso.set('');
    this.mensagemErro.set('');
    this.tarefaParaDeletar.set(null);
    
    console.log('Estado após set:', {
      sucesso: this.mensagemSucesso(),
      aviso: this.mensagemAviso(),
      erro: this.mensagemErro(),
      deletar: this.tarefaParaDeletar()
    });
    
    this.timeoutId = setTimeout(() => {
      console.log('Limpando mensagem de aviso após 8s');
      this.mensagemAviso.set('');
      this.timeoutId = null;
    }, 8000);
  }

  /**
   * Exibe mensagem de erro.
   */
  exibirErro(mensagem: string): void {
    // Limpar timeout anterior se existir
    if (this.timeoutId) {
      clearTimeout(this.timeoutId);
    }
    
    this.mensagemErro.set(mensagem);
    this.mensagemSucesso.set('');
    this.mensagemAviso.set('');
    this.tarefaParaDeletar.set(null);
    
    this.timeoutId = setTimeout(() => {
      this.mensagemErro.set('');
      this.timeoutId = null;
    }, 8000);
  }

  /**
   * Limpa todas as mensagens.
   */
  limparMensagens(): void {
    if (this.timeoutId) {
      clearTimeout(this.timeoutId);
      this.timeoutId = null;
    }
    this.mensagemSucesso.set('');
    this.mensagemAviso.set('');
    this.mensagemErro.set('');
    this.tarefaParaDeletar.set(null);
  }
}
