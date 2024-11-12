import { IProfessor, NewProfessor } from './professor.model';

export const sampleWithRequiredData: IProfessor = {
  id: 28687,
  nome: 'bid',
};

export const sampleWithPartialData: IProfessor = {
  id: 18749,
  nome: 'dime now unbalance',
};

export const sampleWithFullData: IProfessor = {
  id: 11007,
  nome: 'alliance',
};

export const sampleWithNewData: NewProfessor = {
  nome: 'hmph spew sleepily',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
