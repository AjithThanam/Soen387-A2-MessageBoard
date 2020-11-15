import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { LoginService } from '../../services/login/login.service'; 
import { GlobalStateService } from '../../services/global-state/global-state.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username = new FormControl();
  pwd = new FormControl();

  constructor(private loginService: LoginService,
              public state: GlobalStateService,
              private router : Router) { 
  }

  ngOnInit(): void {
    if(this.state.isLoggedIn){
      this.redirectToMain();
    }
  }

  login(){

    let result: any = this.loginService.login(this.username.value, this.pwd.value).subscribe(
      res => {
        if(res.success === "true"){
          let userId = this.username.value;
          let sessionId = res.data.sessionId;

          this.state.setLoggedIn(true);
          this.state.setSessionId(sessionId);
          this.state.setUserId(userId);
          this.redirectToMain();
        }else{
            alert("Invalid credentials provided");
        }
      });
  }

  logoff(){
    this.state.setLoggedIn(false);
    this.router.navigate(['/login']);
  }

  redirectToMain(){
    this.router.navigate(['/home']);
  }

  unsupportedOperation(){
    alert("Unsupported Operation");
  }
}
