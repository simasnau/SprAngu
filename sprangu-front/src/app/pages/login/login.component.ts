import {Component, OnInit} from '@angular/core';
import {UserInfo} from "../../domain/user/model/user-info";
import {UserService} from "../../services/user.service";
import {AuthenticationService} from "../../services/authentication.service";
import {Router} from "@angular/router";
import {AppConstants} from "../../constants/app-constants";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  user = new UserInfo();
  error: boolean;

  constructor(
    private userService: UserService,
    private authenticationService: AuthenticationService,
    private router: Router
  ) {
  }

  ngOnInit(): void {
  }

  register(): void {
    this.userService.createUser(this.user).subscribe(() => this.user = new UserInfo());
  }

  login(): void {
    this.error = false;
    this.userService.login(this.user).subscribe(response => {
        response ? this.authenticationService.user = response : this.user = new UserInfo();
        localStorage.setItem(AppConstants.STORAGE_USER_ID, JSON.stringify(this.authenticationService.user.id));
        localStorage.setItem(AppConstants.STORAGE_USER_NAME, this.authenticationService.user.name);
        this.router.navigate(['/']);
      },
      error => {
        this.error = true;
      })
  }
}
