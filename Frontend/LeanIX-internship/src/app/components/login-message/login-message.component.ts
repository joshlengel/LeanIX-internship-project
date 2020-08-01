import { Component, OnInit, Input } from '@angular/core';
import { LoginState } from 'src/app/models/LoginState';

@Component({
  selector: 'app-login-message',
  templateUrl: './login-message.component.html',
  styleUrls: ['./login-message.component.css']
})
export class LoginMessageComponent implements OnInit {

  @Input() loginMessage: string;
  @Input() loginState: LoginState;

  constructor() { }

  ngOnInit(): void {
  }

  onGetMessageClasses() {
    let classes = {
      'message': true,
      'unsuccessful-signup': this.loginState == LoginState.UNSUCCESSFUL,
      'successful-signup': this.loginState == LoginState.SUCCESSFUL
    };

    return classes;
  }

  isNotPending(): boolean {
    return this.loginState != LoginState.PENDING;
  }
}
