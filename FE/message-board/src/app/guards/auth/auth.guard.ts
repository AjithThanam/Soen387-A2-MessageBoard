import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router} from '@angular/router';
import { Observable } from 'rxjs';

import { GlobalStateService } from '../../services/global-state/global-state.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  
  constructor( private state : GlobalStateService, private router : Router){}

  canActivate () : boolean{
    if(this.state.isLoggedIn){
      return true
    } else{
      this.router.navigate(['/login'])
      return false
    }
  } 
  
  
  /*
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return true;
  }
  */
  
}
