import { IAuthority, NewAuthority } from './authority.model';

export const sampleWithRequiredData: IAuthority = {
  name: '7a0592da-4e8e-4bec-b94c-c10e6cb4b8a6',
};

export const sampleWithPartialData: IAuthority = {
  name: '8871bb5e-1bff-4ccd-bb9c-62bbb5639c18',
};

export const sampleWithFullData: IAuthority = {
  name: '6ce414d4-dcf8-42c2-bf9b-49e2f9f2a65b',
};

export const sampleWithNewData: NewAuthority = {
  name: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
