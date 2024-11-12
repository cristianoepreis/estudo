import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IPlanoEnsino } from '../plano-ensino.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../plano-ensino.test-samples';

import { PlanoEnsinoService } from './plano-ensino.service';

const requireRestSample: IPlanoEnsino = {
  ...sampleWithRequiredData,
};

describe('PlanoEnsino Service', () => {
  let service: PlanoEnsinoService;
  let httpMock: HttpTestingController;
  let expectedResult: IPlanoEnsino | IPlanoEnsino[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(PlanoEnsinoService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a PlanoEnsino', () => {
      const planoEnsino = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(planoEnsino).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PlanoEnsino', () => {
      const planoEnsino = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(planoEnsino).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PlanoEnsino', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PlanoEnsino', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PlanoEnsino', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPlanoEnsinoToCollectionIfMissing', () => {
      it('should add a PlanoEnsino to an empty array', () => {
        const planoEnsino: IPlanoEnsino = sampleWithRequiredData;
        expectedResult = service.addPlanoEnsinoToCollectionIfMissing([], planoEnsino);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(planoEnsino);
      });

      it('should not add a PlanoEnsino to an array that contains it', () => {
        const planoEnsino: IPlanoEnsino = sampleWithRequiredData;
        const planoEnsinoCollection: IPlanoEnsino[] = [
          {
            ...planoEnsino,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPlanoEnsinoToCollectionIfMissing(planoEnsinoCollection, planoEnsino);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PlanoEnsino to an array that doesn't contain it", () => {
        const planoEnsino: IPlanoEnsino = sampleWithRequiredData;
        const planoEnsinoCollection: IPlanoEnsino[] = [sampleWithPartialData];
        expectedResult = service.addPlanoEnsinoToCollectionIfMissing(planoEnsinoCollection, planoEnsino);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(planoEnsino);
      });

      it('should add only unique PlanoEnsino to an array', () => {
        const planoEnsinoArray: IPlanoEnsino[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const planoEnsinoCollection: IPlanoEnsino[] = [sampleWithRequiredData];
        expectedResult = service.addPlanoEnsinoToCollectionIfMissing(planoEnsinoCollection, ...planoEnsinoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const planoEnsino: IPlanoEnsino = sampleWithRequiredData;
        const planoEnsino2: IPlanoEnsino = sampleWithPartialData;
        expectedResult = service.addPlanoEnsinoToCollectionIfMissing([], planoEnsino, planoEnsino2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(planoEnsino);
        expect(expectedResult).toContain(planoEnsino2);
      });

      it('should accept null and undefined values', () => {
        const planoEnsino: IPlanoEnsino = sampleWithRequiredData;
        expectedResult = service.addPlanoEnsinoToCollectionIfMissing([], null, planoEnsino, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(planoEnsino);
      });

      it('should return initial array if no PlanoEnsino is added', () => {
        const planoEnsinoCollection: IPlanoEnsino[] = [sampleWithRequiredData];
        expectedResult = service.addPlanoEnsinoToCollectionIfMissing(planoEnsinoCollection, undefined, null);
        expect(expectedResult).toEqual(planoEnsinoCollection);
      });
    });

    describe('comparePlanoEnsino', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePlanoEnsino(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePlanoEnsino(entity1, entity2);
        const compareResult2 = service.comparePlanoEnsino(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePlanoEnsino(entity1, entity2);
        const compareResult2 = service.comparePlanoEnsino(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePlanoEnsino(entity1, entity2);
        const compareResult2 = service.comparePlanoEnsino(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
