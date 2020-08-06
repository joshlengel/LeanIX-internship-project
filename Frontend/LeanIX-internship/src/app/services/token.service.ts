import { Injectable } from '@angular/core';
import { LoginResult } from './login.service';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  loginResult: LoginResult;

  constructor() { }

  getLoginResult(): LoginResult {
    return this.loginResult;
  }

  setLoginResult(result: LoginResult): void {
    this.loginResult = result;
  }
}
