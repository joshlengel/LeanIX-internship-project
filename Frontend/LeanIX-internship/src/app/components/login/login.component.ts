import { Component, OnInit } from '@angular/core';
import { LoginState } from '../../models/LoginState';
import { Router } from '@angular/router';
import { ApiService } from '../../services/api.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  message: string;
  pending: boolean = true;

  classes;

  constructor(
    private router: Router,
    private api: ApiService) { }

  ngOnInit(): void {}

  onLoginStateChanged(state: LoginState): void {
    if (state !== LoginState.PENDING) {
      this.message = this.api.message;
      this.pending = false;
    }

    this.classes = {
      'login-pending': state === LoginState.PENDING,
      'login-successful': state === LoginState.OK,
      'login-unsuccessful': state !== LoginState.OK && state !== null
    };
  }
}
