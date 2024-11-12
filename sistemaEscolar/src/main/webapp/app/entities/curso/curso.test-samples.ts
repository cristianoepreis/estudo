import { ICurso, NewCurso } from './curso.model';

export const sampleWithRequiredData: ICurso = {
  id: 3157,
  nome: 'duh softly foolishly',
};

export const sampleWithPartialData: ICurso = {
  id: 4970,
  nome: 'before seldom print',
};

export const sampleWithFullData: ICurso = {
  id: 28938,
  nome: 'provided consequently',
};

export const sampleWithNewData: NewCurso = {
  nome: 'yahoo considering',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
