import { Component, OnInit } from '@angular/core';
import { Post } from '../../models/Post';
import { PostService } from '../../services/post/post.service';

@Component({
  selector: 'app-post-list',
  templateUrl: './post-list.component.html',
  styleUrls: ['./post-list.component.css']
})
export class PostListComponent implements OnInit {

  currentPosts: Post[];

  constructor(private service: PostService) { }

  ngOnInit(): void {
    this.service.getPost(null, null,null).subscribe((posts) => this.currentPosts = posts);
  }


  //  5 different types of filter - by on User Posted, date range (start and end) 
  loadPost(){

  }

  findMyPost(){

  }

  findPostByDateRange(){

  }


  findMostRecentPost(){

  }

}
