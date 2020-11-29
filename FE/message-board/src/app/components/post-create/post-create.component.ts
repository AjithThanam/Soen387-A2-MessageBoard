import { Component, OnInit } from '@angular/core';
import { Post } from '../../models/Post';

import { GlobalStateService } from '../../services/global-state/global-state.service';
import { FormControl } from '@angular/forms';

import { PostService } from '../../services/post/post.service';


@Component({
  selector: 'app-post-create',
  templateUrl: './post-create.component.html',
  styleUrls: ['./post-create.component.css']
})
export class PostCreateComponent implements OnInit {

  newPost: Post;
  newTitle = new FormControl();
  newMessage = new FormControl(); 

  files: File[] = [];

  formHTML: string; 

  constructor(private service: PostService, private state: GlobalStateService) { }

  ngOnInit(): void {
    this.newPost = new Post();
    this.newPost.userId = this.state.userId;
    this.newPost.date_time = null;
    this.newPost.last_modded = null;
    this.newPost.id = null;
  }


  async savePost(){
    this.newPost.title = this.newTitle.value; 
    this.newPost.message = this.newMessage.value;
    alert(this.newTitle.value + " " + this.newMessage.value);
    var fs = this.files;
    
    let response = await this.service.createPost(this.newPost, this.files);
    /*
   this.service.createPost(this.newPost, this.files).subscribe(res => {
      
      console.log({res})
    })
    */
  }


  //Using external lib: https://www.npmjs.com/package/ngx-dropzone
  onSelect(event) {
    this.files.push(...event.addedFiles);
  }
 
  onRemove(event) {
    this.files.splice(this.files.indexOf(event), 1);
  }
}
