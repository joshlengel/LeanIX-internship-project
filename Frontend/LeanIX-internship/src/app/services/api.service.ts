import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { map, catchError, flatMap } from 'rxjs/operators';
import { Observable, throwError } from 'rxjs';
import * as jwt from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  private cacheUsername: string;
  private cachePassword: string;
  private cacheRoles: string[];
  private token: string;

  message: string = null;
  verified: boolean = false;

  constructor(private http: HttpClient, private router: Router) { }

  login(username: string, password: string): Observable<any> {
    return this.http.post('/api/login', {
      username: username,
      password: password
    }, {
      headers: new HttpHeaders().set('content-type', 'application/json'),
      responseType: 'text'
    }).pipe(
      map(result => {
        this.cacheUsername = username;
        this.cachePassword = password;
        this.token = result;
        this.verified = true;

        // Decode token
        let user = jwt(result);
        this.cacheRoles = user['roles'];

        this.router.navigateByUrl(`/account/${username}`);
      }),
      catchError((err: HttpErrorResponse) => {
        switch (err.status) {
          case 401: this.message = 'Wrong username of password'; break;
          default:  this.message = 'Something went wrong!';      break;
        }

        return throwError(err);
      })
    );
  }

  private tryTwice<T>(obsProd: () => Observable<T>): Observable<T> {
    return obsProd().pipe<T>(
      catchError((err: HttpErrorResponse) => {
        if (err.status === 504) {
          return this.http.post('/api/login', {
            username: this.cacheUsername,
            password: this.cachePassword
          }, {
            headers: new HttpHeaders().set('content-type', 'application/json'),
            responseType: 'text'
          }).pipe(
            flatMap(newToken => {
              this.token = newToken;

              return obsProd().pipe(
                catchError((err: HttpErrorResponse) => {
                  this.router.navigateByUrl('/login');
                  return throwError(err);
                })
              );
            })
          )
        } else {
          return throwError(err);
        }
      })
    );
  }

  getHiddenData(): Observable<string> {
    return this.tryTwice<string>(
      () => this.http.get(`/api/account/${this.cacheUsername}/data`, {
          headers: new HttpHeaders().set('Authorization', this.token),
          responseType: 'text'
        }));
  }

  getUsername(): string { return this.cacheUsername; }
  getPassword(): string { return this.cachePassword; }
  getRoles(): string[] { return this.cacheRoles; }
}
