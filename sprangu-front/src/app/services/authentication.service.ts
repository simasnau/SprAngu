import {Injectable} from '@angular/core';
import {UserInfo} from "../domain/user/model/user-info";
import {AppConstants} from "../constants/app-constants";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  user = new UserInfo();

  constructor(
    private router: Router
  ) {
  }

  isLogged(): boolean {
    return this.user.id > 0;
  }

  logout(): void {
    this.user = new UserInfo();
    localStorage.removeItem(AppConstants.STORAGE_USER_NAME);
    localStorage.removeItem(AppConstants.STORAGE_USER_ID);
    this.router.navigate(['/']);
  }

  init(): void {
    const userId = localStorage.getItem(AppConstants.STORAGE_USER_ID);
    const userName = localStorage.getItem(AppConstants.STORAGE_USER_NAME);
    if (userId && userName) {
      this.user = new UserInfo();
      this.user.id = Number(userId);
      this.user.name = userName;
    }
  }
}
