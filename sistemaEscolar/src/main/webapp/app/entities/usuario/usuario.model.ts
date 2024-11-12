export interface IUsuario {
  id: number;
  nome?: string | null;
  email?: string | null;
  senha?: string | null;
}

export type NewUsuario = Omit<IUsuario, 'id'> & { id: null };
