import { IDisciplina } from 'app/entities/disciplina/disciplina.model';

export interface ICurso {
  id: number;
  nome?: string | null;
  disciplinas?: IDisciplina[] | null;
}

export type NewCurso = Omit<ICurso, 'id'> & { id: null };
