import { IDisciplina } from 'app/entities/disciplina/disciplina.model';
import { IProfessor } from 'app/entities/professor/professor.model';

export interface IPlanoEnsino {
  id: number;
  ementa?: string | null;
  bibliografiaBasica?: string | null;
  bibliografiaComplementar?: string | null;
  praticaLaboratorial?: string | null;
  disciplina?: IDisciplina | null;
  professorResponsavel?: IProfessor | null;
}

export type NewPlanoEnsino = Omit<IPlanoEnsino, 'id'> & { id: null };
