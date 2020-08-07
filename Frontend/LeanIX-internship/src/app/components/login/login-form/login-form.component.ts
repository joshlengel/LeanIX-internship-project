import { Component, OnInit, EventEmitter, Output } from '@angular/core';
import { LoginState } from '../../../models/LoginState';
import { ApiService } from '../../../services/api.service'

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit {

  username: string;
  password: string;

  @Output() loginStateChanged: EventEmitter<LoginState> = new EventEmitter();

  constructor(private api: ApiService) { }

  ngOnInit(): void {
    this.loginStateChanged.emit(LoginState.PENDING);
  }

  onSubmit(): void {
    this.api.login(this.username, this.password).subscribe(
      result => this.loginStateChanged.emit(LoginState.OK),
      error => {
        let state: LoginState;

        switch(error.status) {
          case 401: state = LoginState.WRONG_USERNAME_OR_PASSWORD; break;
          default:  state = LoginState.INTERNAL_ERROR; break;
        }

        this.loginStateChanged.emit(state);
      }
    );
  }
}
