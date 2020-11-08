
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PostListComponent } from './components/post-list/post-list.component';
import { LoginComponent } from './components/login/login.component';
import { AuthGuard } from './guards/auth/auth.guard';
import { PostCreateComponent } from './components/post-create/post-create.component';


const routes: Routes = [
    {path: 'home', component: PostListComponent, canActivate : [AuthGuard]},
    {path: 'login', component: LoginComponent},
    {path: 'create', component: PostCreateComponent, canActivate : [AuthGuard]}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule{}