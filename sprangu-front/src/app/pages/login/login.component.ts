import {Component, OnInit} from '@angular/core';
import {UserInfo} from "../../domain/user/model/user-info";
import {AuthenticationService} from "../../services/authentication.service";
import {Router} from "@angular/router";
import {JwtConstants} from "../../constants/jwt-constants";
import {CookieService} from "ngx-cookie-service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  user = new UserInfo();
  error: boolean;

  constructor(
    private authenticationService: AuthenticationService,
    private router: Router,
    private cookieService: CookieService
  ) {
  }

  ngOnInit(): void {
  }

  register(): void {
    this.authenticationService.createUser(this.user).subscribe(() => this.user = new UserInfo());
  }

  login(): void {
    this.error = false;
    this.authenticationService.login(this.user).subscribe(token => {
        console.log(token);
        this.cookieService.set(JwtConstants.JWT, token, {
          path: '/'
        });
        this.authenticationService.resolveUser();
        this.router.navigate(['/']);
      },
      error => {
        this.error = true;
      })
  }
}
