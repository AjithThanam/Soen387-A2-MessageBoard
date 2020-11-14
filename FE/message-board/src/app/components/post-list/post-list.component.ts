import { Component, OnInit } from '@angular/core';
import { Post } from '../../models/Post';
import { PostService } from '../../services/post/post.service';

import { FormControl } from '@angular/forms';
import { GlobalStateService } from '../../services/global-state/global-state.service';


@Component({
  selector: 'app-post-list',
  templateUrl: './post-list.component.html',
  styleUrls: ['./post-list.component.css']
})
export class PostListComponent implements OnInit {

  currentPosts: Post[];

  start = new FormControl();
  end = new FormControl();
  hashTags = new FormControl();

  constructor(private service: PostService, private state: GlobalStateService) { }

  ngOnInit(): void {
    this.findMostRecentPost();
  }


  //  5 different types of filter - by on User Posted, date range (start and end) 

  findMyPosts(){
    this.service.getPost(this.state.userId, null,null, null).subscribe((posts) => this.currentPosts = posts);
  }

  findPostByDateRange(){
    let startDate = this.start.value.split("T")[0];
    let endDate =  this.end.value.split("T")[0];
    //yyyy-mm-dd

    this.service.getPost(null, startDate,endDate, null).subscribe((posts) => this.currentPosts = posts);
  }

  findMostRecentPost(){
     this.service.getPost(null, null,null, null).subscribe((posts) => this.currentPosts = posts);
  }

  findByHashTag(){
      this.service.getPost(null, null,null, this.hashTags.value).subscribe((posts) => this.currentPosts = posts);
  }

  deletePost(id: any){
    debugger;
    this.service.deletePost(id).subscribe( (res: any) => {
        alert(res);

        if(res.success){
        for(let i = 0; i < this.currentPosts.length; i++){
          if(this.currentPosts[i].id == id){
            this.currentPosts.splice(i, 1);
         }
        }
        }else{
          alert("Delete failed");
        }
    });
    
  }

  isMyPost(post: Post):boolean{
    let match = post.userId === this.state.userId;
    return match;
  }

  setEditPost(post: Post){
    debugger;
    this.state.setEditPost(post);
    window.location.href = '/assets/pages/post-edit.html';
  }

  downloadAttachments(postId: any){
    alert(postId);
    const postID = encodeURI(postId);
    var filterUrl = new URL("url");
    filterUrl.searchParams.append("postID", postID);
    //window.location.href = filterUrl;
  }
}
