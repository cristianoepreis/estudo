import { IPlanoEnsino, NewPlanoEnsino } from './plano-ensino.model';

export const sampleWithRequiredData: IPlanoEnsino = {
  id: 7637,
  ementa: 'lest',
  bibliografiaBasica: 'probable greedy afore',
  bibliografiaComplementar: 'tame',
  praticaLaboratorial: 'puff psst vain',
};

export const sampleWithPartialData: IPlanoEnsino = {
  id: 28313,
  ementa: 'untried surface',
  bibliografiaBasica: 'scowl daintily circulate',
  bibliografiaComplementar: 'exotic yesterday interviewer',
  praticaLaboratorial: 'joshingly',
};

export const sampleWithFullData: IPlanoEnsino = {
  id: 31042,
  ementa: 'boo',
  bibliografiaBasica: 'collaboration furthermore',
  bibliografiaComplementar: 'powerless wonderful contrail',
  praticaLaboratorial: 'shout',
};

export const sampleWithNewData: NewPlanoEnsino = {
  ementa: 'slowly scramble tray',
  bibliografiaBasica: 'boohoo',
  bibliografiaComplementar: 'instead beyond',
  praticaLaboratorial: 'flat knowingly since',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
