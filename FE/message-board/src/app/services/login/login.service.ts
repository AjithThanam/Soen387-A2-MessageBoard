import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { mergeMap } from 'rxjs/operators';
import { Observable, of } from 'rxjs';
import { GlobalStateService } from '../../services/global-state/global-state.service';
import { Router } from '@angular/router';
import { environment } from '../../config/environment'

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http: HttpClient, private state: GlobalStateService) { }

  login(username: string, password: string): Observable<any> {
    debugger;
    let domain = environment["domain"];
    let url = domain + '/MessageBoard_war_exploded/MessageBoardServlet'; 
    const payload = new HttpParams().set('email', username).set('password', password);
    let httpOptions = {
        headers: new HttpHeaders({ 'Content-Type': 'application/x-www-form-urlencoded' })
    }; 
    return this.http.post<any>(url, payload, httpOptions);
  }

}
