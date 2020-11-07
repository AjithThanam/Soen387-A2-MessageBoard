import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class GlobalStateService {

  isLoggedIn: boolean;
  userId: string;

  constructor() { 
    this.setUp();
  }

  setUp(){
    this.isLoggedIn = localStorage.getItem('loggedIn') != null && localStorage.getItem('loggedIn') === 'true';
    this.userId = localStorage.getItem('id');

  }

  setLoggedIn(loggedIn: boolean){
    this.isLoggedIn = loggedIn;
    localStorage.setItem('loggedIn', loggedIn + "");
  }

  setUserId(id: string){
    this.userId = id;
    localStorage.setItem('id', id);
  }
}
