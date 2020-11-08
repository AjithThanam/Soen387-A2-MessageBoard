import { Component, OnInit } from '@angular/core';
import { Post } from '../../models/Post';

import { GlobalStateService } from '../../services/global-state/global-state.service';
import { FormControl } from '@angular/forms';


@Component({
  selector: 'app-post-create',
  templateUrl: './post-create.component.html',
  styleUrls: ['./post-create.component.css']
})
export class PostCreateComponent implements OnInit {

  newPost: Post;
  newTitle = new FormControl();
  newMessage = new FormControl(); 

  constructor(private state: GlobalStateService) { }

  ngOnInit(): void {
    this.newPost = new Post();
    this.newPost.userId = this.state.userId;
    this.newPost.date_time = null;
    this.newPost.last_modded = null;
    this.newPost.id = null;
  }

  savePost(){
    alert(this.newTitle.value + " " + this.newMessage.value);
  }

}
