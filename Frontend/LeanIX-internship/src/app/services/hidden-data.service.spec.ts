import { TestBed } from '@angular/core/testing';

import { HiddenDataService } from './hidden-data.service';

describe('HiddenDataService', () => {
  let service: HiddenDataService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HiddenDataService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
