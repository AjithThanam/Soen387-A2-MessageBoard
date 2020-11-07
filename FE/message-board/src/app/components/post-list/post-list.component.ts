import { Component, OnInit } from '@angular/core';
import { Post } from '../../models/Post';
import { PostService } from '../../services/post/post.service';

import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-post-list',
  templateUrl: './post-list.component.html',
  styleUrls: ['./post-list.component.css']
})
export class PostListComponent implements OnInit {

  currentPosts: Post[];

  start = new FormControl();
  end = new FormControl();

  constructor(private service: PostService) { }

  ngOnInit(): void {
    this.findMostRecentPost();
  }


  //  5 different types of filter - by on User Posted, date range (start and end) 

  findMyPosts(){

  }

  findPostByDateRange(){
    alert(this.start.value + " " + this.end.value);

  }

  findMostRecentPost(){
     this.service.getPost(null, null,null).subscribe((posts) => this.currentPosts = posts);
  }
}
