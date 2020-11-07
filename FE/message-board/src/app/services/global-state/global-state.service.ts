import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class GlobalStateService {

  isLoggedIn: boolean;

  constructor() { 
    this.setUp();
  }

  setUp(){
    this.isLoggedIn = localStorage.getItem('loggedIn') != null && localStorage.getItem('loggedIn') === 'true';
  }

  setLoggedIn(loggedIn: boolean){
    this.isLoggedIn = loggedIn;
    localStorage.setItem('loggedIn', loggedIn + "");
  }
}
