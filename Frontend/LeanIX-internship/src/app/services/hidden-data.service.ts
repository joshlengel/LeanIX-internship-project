import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HiddenDataService {

  constructor(private http: HttpClient) { }

  getHiddenData(loginData, loginToken: string): Observable<string> {
    return this.http.get(`api/account/${loginData.username}/data`, {
      headers: new HttpHeaders().set('Authorization', loginToken),
      responseType: 'text'
    });
  }
}
