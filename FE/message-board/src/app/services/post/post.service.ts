import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';

import { interval, fromEvent, of } from 'rxjs';
import { switchMap } from 'rxjs/operators';

import { Post } from '../../models/Post'

@Injectable({
  providedIn: 'root'
})
export class PostService {
  baseURL: string;



  constructor(private http: HttpClient) { }

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
}
