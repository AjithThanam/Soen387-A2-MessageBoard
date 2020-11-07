import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';

import { LoginService } from '../../services/login/login.service'; 
import { GlobalStateService } from '../../services/global-state/global-state.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username = new FormControl();
  pwd = new FormControl();

  constructor(private loginService: LoginService,
              public state: GlobalStateService) { 
  }

  ngOnInit(): void {
    alert("IS Logged in " + this.state.isLoggedIn);
  }

  login(){
    let result: boolean = this.loginService.login(this.username.value, this.pwd.value);
    this.state.setLoggedIn(result);
  }

  logoff(){
    this.state.setLoggedIn(false);
  }

}
