import { TestBed } from '@angular/core/testing';

import { LoginMessageService } from './login-message.service';

describe('LoginMessageService', () => {
  let service: LoginMessageService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoginMessageService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
