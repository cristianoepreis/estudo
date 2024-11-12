import { IDisciplina, NewDisciplina } from './disciplina.model';

export const sampleWithRequiredData: IDisciplina = {
  id: 32252,
  nome: 'hunt',
};

export const sampleWithPartialData: IDisciplina = {
  id: 20239,
  nome: 'after',
};

export const sampleWithFullData: IDisciplina = {
  id: 23327,
  nome: 'ew rubric furthermore',
};

export const sampleWithNewData: NewDisciplina = {
  nome: 'wherever',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
