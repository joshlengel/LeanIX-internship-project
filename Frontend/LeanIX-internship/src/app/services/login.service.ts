import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams, HttpErrorResponse } from '@angular/common/http';
import { Observable, ObservableInput, throwError } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http: HttpClient) { }

  login(username: string, password: string): Observable<any> {
    let headers: HttpHeaders = new HttpHeaders()
      .set('content-type', 'application/json');

    return this.http.post<any>("login", {
      'username': username,
      'password': password
    }, {
      headers: headers
    }).pipe(
      tap(data => console.log(data)),
      catchError(this.handleError)
      );
  }

  private handleError(error: HttpErrorResponse): ObservableInput<any> {
    let errMsg: string = '';

    if (error.error instanceof ErrorEvent) {
      errMsg = `Server error response: '${error.error.message}'`;
    } else {
      errMsg = `Server response code: '${error.status}'. Server response message: ${error.message}`;
    }

    return throwError(errMsg);
  }
}
