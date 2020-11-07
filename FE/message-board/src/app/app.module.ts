import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { LoginService } from './services/login/login.service'; 
import { GlobalStateService } from './services/global-state/global-state.service';
import { PostListComponent } from './components/post-list/post-list.component';
import { PostService } from './services/post/post.service';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    PostListComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [LoginService, GlobalStateService, PostService],
  bootstrap: [AppComponent]
})
export class AppModule { }
