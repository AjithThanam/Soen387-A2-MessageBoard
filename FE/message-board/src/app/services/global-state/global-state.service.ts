import { Injectable } from '@angular/core';
import { environment } from '../../config/environment'


@Injectable({
  providedIn: 'root'
})
export class GlobalStateService {

  isLoggedIn: boolean;
  userId: string;
  sessionId: string;

  constructor() { 
    this.setUp();
  }

  setUp(){
    this.isLoggedIn = localStorage.getItem('loggedIn') != null && localStorage.getItem('loggedIn') === 'true';
    this.userId = localStorage.getItem('id');
    this.sessionId = localStorage.getItem('ses');
    localStorage.setItem('domain', environment["domain"]);
  }

  setLoggedIn(loggedIn: boolean){
    this.isLoggedIn = loggedIn;
    localStorage.setItem('loggedIn', loggedIn + "");
  }

  setUserId(id: string){
    this.userId = id;
    localStorage.setItem('id', id);
  }

  setSessionId(session: string){
    this.sessionId = session;
    localStorage.setItem('ses', session);
  }

  setEditPost(post: any){
    let postContent = JSON.stringify(post);
    localStorage.setItem("edit-post", postContent); 
  }
}
