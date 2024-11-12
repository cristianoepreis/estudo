export interface IProfessor {
  id: number;
  nome?: string | null;
}

export type NewProfessor = Omit<IProfessor, 'id'> & { id: null };
