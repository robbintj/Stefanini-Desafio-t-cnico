export interface Tarefa {
  id?: number;
  titulo: string;
  descricao: string;
  status: StatusTarefa;
  dataCriacao?: string;
  dataAtualizacao?: string;
}

export enum StatusTarefa {
  PENDENTE = 'PENDENTE',
  EM_ANDAMENTO = 'EM_ANDAMENTO',
  CONCLUIDA = 'CONCLUIDA'
}

export interface TarefaCreate {
  titulo: string;
  descricao: string;
  status?: StatusTarefa;
}

export interface TarefaUpdate {
  titulo: string;
  descricao: string;
  status: StatusTarefa;
}

