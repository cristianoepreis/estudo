import { IUsuario, NewUsuario } from './usuario.model';

export const sampleWithRequiredData: IUsuario = {
  id: 16034,
  nome: 'how miserably',
  email: 'AnaJulia71@yahoo.com',
  senha: 'supposing if boohoo',
};

export const sampleWithPartialData: IUsuario = {
  id: 20130,
  nome: 'oof fortunately',
  email: 'Caua_Reis@yahoo.com',
  senha: 'heroine consequently',
};

export const sampleWithFullData: IUsuario = {
  id: 211,
  nome: 'whenever',
  email: 'Benicio_Pereira@live.com',
  senha: 'pronounX',
};

export const sampleWithNewData: NewUsuario = {
  nome: 'yahoo',
  email: 'Rebeca_Barros24@yahoo.com',
  senha: 'byXXXXXX',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
