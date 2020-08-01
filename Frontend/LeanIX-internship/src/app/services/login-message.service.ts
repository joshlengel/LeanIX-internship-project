import { Injectable } from '@angular/core';
import { LoginState } from '../models/LoginState';

@Injectable({
  providedIn: 'root'
})
export class LoginMessageService {

  messages: Map<LoginState, string>;

  constructor() { 
    this.messages = new Map();
    this.messages.set(LoginState.PENDING, 'Enter username and password');
    this.messages.set(LoginState.SUCCESSFUL, 'Welcome back!');
    this.messages.set(LoginState.UNSUCCESSFUL, 'Incorrect username or password, try again');
  }

  getMessage(result: LoginState): string {
    return this.messages.get(result);
  }
}
