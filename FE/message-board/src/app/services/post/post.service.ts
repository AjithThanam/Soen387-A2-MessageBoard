import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';

import { interval, fromEvent, of } from 'rxjs';
import { switchMap } from 'rxjs/operators';

import { Post } from '../../models/Post'
import { GlobalStateService } from '../../services/global-state/global-state.service';

@Injectable({
  providedIn: 'root'
})
export class PostService {
  baseURL: string;



  constructor(private http: HttpClient, private state: GlobalStateService) { }

  getPost(myPost: string, start: Date, end: Date): Observable<Post[]>{

    let useFilter = false;
    let url = this.baseURL + "/posts";
    //posts?userId=1&start=date&end=data

    if(myPost != null){
      useFilter = true;
      url = url + "?" + "userid=" + myPost; 
    }

    if(start != null){
      if(!useFilter){
        url = url + "?" + "start=" + this.formatDateStr(start) + "&end=" + this.formatDateStr(end); 
      }else{
        url = url + "&start=" + this.formatDateStr(start) + "&end=" + this.formatDateStr(end);
      }
    }

    url = 'http://echo.jsontest.com/key/value/one/two';

    return this.http.get(url, {responseType: 'json'}).pipe(
      switchMap(resp => {
         console.log({resp});
         let posts: Post[] = [];
         let p = new Post();
         p.title = "new Title";
         p.message = "some msg";
         posts.push(p);
         return of(posts);
      })
    );
  }

  formatDateStr(date: Date){
    return "";
  }


  async createPost(post: Post, files: File[]){
    
    //https://bezkoder.com/angular-10-file-upload/

    debugger;
    let sessionID = this.state.sessionId;
    let url = 'http://localhost:8080/MessageBoard_war_exploded/attachments'; 
    
    const payload = new FormData();
    payload.append('title', post.title)
    payload.append('message', post.message);
    payload.append('session', sessionID);
    payload.append('file', files[0]);

    
    
    //let httpOptions = {
    //    headers: new HttpHeaders({ 'Content-Type': 'multipart/form-data; boundary=----WebKitFormBoundary5jyXFqPWuwEnGBq5'})
    //}; 

/*
    let httpOptions = {
        headers: new HttpHeaders({ 'Content-Type': undefined,
                                'enctype': "multipart/form-data"})
    }; 
*/
/*
    $.ajax({
    type: ppiFormMethod,
    data: data,
    url: ppiFormActionURL,
    cache: false,
    contentType: false,
    processData: false,
    success: function(data){
        alert(data);
    }
});
  */
    
    return null;
    //return this.http.post<any>(url, payload, httpOptions);
  }
}
