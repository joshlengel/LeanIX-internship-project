import { Component, OnInit } from '@angular/core';
import { LoginState } from '../../models/LoginState';
import { LoginMessageService } from '../../services/login-message.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  state: LoginState;

  constructor(private messageService: LoginMessageService) { }

  ngOnInit(): void {
  }

  onLoginStateChanged(state: LoginState): void {
    this.state = state;
  }

  getLoginMessage(): string {
    return this.messageService.getMessage(this.state);
  }
}
