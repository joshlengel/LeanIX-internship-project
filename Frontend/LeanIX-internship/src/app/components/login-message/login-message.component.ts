import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-login-message',
  templateUrl: './login-message.component.html',
  styleUrls: ['./login-message.component.css']
})
export class LoginMessageComponent implements OnInit {

  @Input() loginMessage: string;

  constructor() { }

  ngOnInit(): void {
  }
}
