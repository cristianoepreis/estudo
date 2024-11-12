import { IProfessor } from 'app/entities/professor/professor.model';
import { ICurso } from 'app/entities/curso/curso.model';

export interface IDisciplina {
  id: number;
  nome?: string | null;
  professorResponsavel?: IProfessor | null;
  cursos?: ICurso[] | null;
}

export type NewDisciplina = Omit<IDisciplina, 'id'> & { id: null };
