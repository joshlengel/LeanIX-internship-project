import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { LoginState } from '../models/LoginState';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

export class LoginResult {
  state: LoginState;
  data?: { username: string, password: string };
  token?: string;
}

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  verified: boolean;

  constructor(private http: HttpClient) { }

  login(username: string, password: string): Observable<any> {
    return this.http.post("/api/login", {
      'username': username,
      'password': password
    }, {
      headers: new HttpHeaders().set('content-type', 'application/json'),
      responseType: 'text'
    }).pipe(
      tap(result => this.verified = true,
          error => this.verified = false)
    );
  }
}
