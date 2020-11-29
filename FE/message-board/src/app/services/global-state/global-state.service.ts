import { Injectable } from '@angular/core';
import { environment } from '../../config/environment'


@Injectable({
  providedIn: 'root'
})
export class GlobalStateService {

  isLoggedIn: boolean;
  userId: string;
  sessionId: string;
  groups: string[]; //list should contain all the parent and children group user has access to.

  constructor() { 
    this.setUp();
  }

  setUp(){
    this.isLoggedIn = localStorage.getItem('loggedIn') != null && localStorage.getItem('loggedIn') === 'true';
    this.userId = localStorage.getItem('id');
    this.sessionId = localStorage.getItem('ses');
    this.groups = JSON.parse(localStorage.getItem('groups'));
    localStorage.setItem('domain', environment["domain"]);
  }

  setLoggedIn(loggedIn: boolean){
    
    this.isLoggedIn = loggedIn;
    localStorage.setItem('loggedIn', loggedIn + "");
    
    if(!loggedIn){
      this.logoff();
    }
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

  setUserGroups(userGroups: string[]){
    this.groups = userGroups;
    localStorage.setItem('groups', JSON.stringify(userGroups));
  }

  logoff(){
    this.setUserId("");
    this.setSessionId("");
    this.setUserGroups([]);
  }
}
