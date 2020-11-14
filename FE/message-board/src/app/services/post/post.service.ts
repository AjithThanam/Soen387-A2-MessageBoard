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
  baseURL: string = "https://105fc4c7fcba.ngrok.io/MessageBoard_war_exploded/UserPost";


  constructor(private http: HttpClient, private state: GlobalStateService) { }

  getPost(myPost: string, start: Date, end: Date, hashTags: string): Observable<Post[]>{

    let useFilter = false;
    let url = this.baseURL;
    //posts?userId=1&start=date&end=data

    if(myPost != null){
      useFilter = true;
      url = url + "?" + "userid=" + encodeURIComponent(myPost); 
    }

    if(start != null){
      if(!useFilter){
        url = url + "?" + "start=" + encodeURIComponent(this.formatDateStr(start)) + "&end=" + encodeURIComponent(this.formatDateStr(end)); 
        useFilter = true;
      }else{
        url = url + "&start=" + encodeURIComponent(this.formatDateStr(start)) + "&end=" + encodeURIComponent(this.formatDateStr(end));
      }
    }

    if(hashTags != null){
      let andText = "";
      
      if(!useFilter){
        andText = "?";
      }else{
        andText = "&";
      }    
      url = url + andText + "hashtag=" + encodeURIComponent(hashTags);  
    }

    //alert(url);

    /*
    return this.http.get(url, {responseType: 'json'}).pipe(
      switchMap((resp: any) => {
         let postsJSONArr: any = resp.posts;

         let posts: Post[] = [];
         for(let i = 0; i < postsJSONArr.length; i++){
            let p = new Post();
            let jsonPost = postsJSONArr[i];
            p.date_time = jsonPost.dateTime;
            p.id = jsonPost.postId;
            p.last_modded = jsonPost.lastModified;
            p.message = jsonPost.message;
            p.title = jsonPost.title;
            p.userId = jsonPost.username;

            let tagJson = jsonPost.hashtag;
            let tagstr = "";
            for(let j = 0; j < tagJson.length; j++){
               tagstr = tagstr + " " + tagJson[j];
            }
            p.tags = tagstr;
            posts.push(p);
         }  

         return of(posts);
      })
    );
    */
    return of(this.getMockPost());
  }

  formatDateStr(date: any){
    return date;
  }

  deletePost(id: string){
    let url = this.baseURL + "?" + "postId=" + encodeURIComponent(id);
    return this.http.get(url, {responseType: 'json'});
  }

  getMockPost(): Post[]{
    let posts = [];

    let p = new Post();
    p.id = 1;
    p.message = "MES1";
    p.title = "Title1";
    p.userId = "alice@gmail.com"

    let p2 = new Post();
    p2.id = 2;
    p2.message = "MES2";
    p2.title = "Title2";
    p2.userId = "evan@gmail.com"

    posts.push(p);
    posts.push(p2);

    return posts;
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
    
    return null;
    //return this.http.post<any>(url, payload, httpOptions);
  }
}
