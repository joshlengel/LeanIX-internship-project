import { Component, OnInit, EventEmitter, Output } from '@angular/core';
import { LoginService } from '../../services/login.service';
import { LoginState } from '../../models/LoginState';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit {

  username: string;
  password: string;
  loginState: LoginState;

  @Output() loginStateChanged: EventEmitter<LoginState> = new EventEmitter();

  constructor(private loginService: LoginService) { }

  ngOnInit(): void {
    this.loginState = LoginState.PENDING;
    this.loginStateChanged.emit(this.loginState);
  }

  onSubmit(): void {
    this.loginService.login(this.username, this.password).subscribe(
      result => {
        this.loginState = LoginState.SUCCESSFUL;
        this.loginStateChanged.emit(this.loginState);
      },
      error => {
        this.loginState = LoginState.UNSUCCESSFUL;
        this.loginStateChanged.emit(this.loginState);
      }
    );
  }
}
