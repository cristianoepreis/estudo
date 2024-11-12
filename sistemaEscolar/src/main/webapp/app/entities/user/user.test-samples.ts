import { IUser } from './user.model';

export const sampleWithRequiredData: IUser = {
  id: 31795,
  login: 'yIMPs',
};

export const sampleWithPartialData: IUser = {
  id: 21597,
  login: 'VQ58',
};

export const sampleWithFullData: IUser = {
  id: 29852,
  login: 'Z9O=c`@ljGm\\7G0OX\\ub5aMP\\ip',
};
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
