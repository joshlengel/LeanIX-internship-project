import { Component, OnInit } from '@angular/core';
import { LoginState } from '../../models/LoginState';
import { Router } from '@angular/router';
import { LoginResult } from 'src/app/services/login.service';
import { LoginService } from '../../services/login.service';
import { TokenService } from '../../services/token.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  state: LoginState;
  message: string;

  classes;

  constructor(
    private router: Router,
    private loginService: LoginService,
    private tokenService: TokenService) { }

  ngOnInit(): void {
    this.loginService.verified = false;
  }

  onLoginStateChanged(login: LoginResult): void {
    this.state = login.state;

    this.tokenService.setLoginResult(login);
    
    switch (this.state) {
      case LoginState.PENDING:                    this.message = null; break;
      case LoginState.WRONG_USERNAME_OR_PASSWORD: this.message = 'Wrong username or password'; break;
      case LoginState.INTERNAL_ERROR:             this.message = 'Something went wrong!'; break;
      case LoginState.OK:                         this.message = `Welcome back, ${login.data.username}!`;
        this.router.navigateByUrl(`account/${login.data.username}`);
        break;
    }

    this.classes = {
      'login-pending': this.state === LoginState.PENDING,
      'login-successful': this.state === LoginState.OK,
      'login-unsuccessful': this.state !== LoginState.OK && this.state !== null
    };
  }

  isNotPending(): boolean {
    return this.state !== LoginState.PENDING;
  }
}
