import {Injectable} from '@angular/core';
import {UserInfo} from "../domain/user/model/user-info";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  user = new UserInfo();

  constructor() {
  }

  isLogged(): boolean {
    return this.user.id > 0;
  }

  logout() {
    this.user = new UserInfo();
  }
}
