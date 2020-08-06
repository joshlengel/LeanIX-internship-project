import { Component, OnInit, EventEmitter, Output } from '@angular/core';
import { LoginService, LoginResult } from '../../../services/login.service';
import { LoginState } from '../../../models/LoginState';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit {

  username: string;
  password: string;
  loginState: LoginState;

  @Output() loginStateChanged: EventEmitter<LoginResult> = new EventEmitter();

  constructor(private loginService: LoginService) { }

  ngOnInit(): void {
    this.loginState = LoginState.PENDING;
    this.loginStateChanged.emit({
      state: this.loginState
    });
  }

  onSubmit(): void {
    this.loginService.login(this.username, this.password).subscribe(
      result => {
        let loginResult: LoginResult = {
          data: {
            'username': this.username,
            'password': this.password
          },
          state: LoginState.OK,
          token: result
        };

        this.loginStateChanged.emit(loginResult);
      },
      error => {
        let state: LoginState;

        switch(error.status) {
          case 401: state = LoginState.WRONG_USERNAME_OR_PASSWORD; break;
          default:  state = LoginState.INTERNAL_ERROR; break;
        }

        this.loginStateChanged.emit({ 'state': state });
      }
    );
  }
}
