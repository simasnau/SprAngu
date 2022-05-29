import {Component} from '@angular/core';
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
export class LoginComponent {

  user = new UserInfo();
  error: string | null;

  constructor(
    private authenticationService: AuthenticationService,
    private router: Router,
    private cookieService: CookieService
  ) {
  }

  register(): void {
    this.authenticationService.createUser(this.user).subscribe(() => this.user = new UserInfo());

    this.authenticationService.createUser(this.user).subscribe({
      next: () => this.user = new UserInfo(),
      error: err => {
        this.error = this.getErrorMessage(err)
      }
    });
  }

  private getErrorMessage(err: any) {
    if (err.error?.trace) {
      return err.error.trace.split("at")[0]
    }
    return JSON.parse(err.error).trace.toString().split("at")[0];
  }

  login(): void {
    this.error = null;
    this.authenticationService.login(this.user).subscribe({
      next: token => {
        this.cookieService.set(JwtConstants.JWT, token, {
          path: '/'
        });
        this.authenticationService.resolveUser();
        this.router.navigate(['/']);
      },
      error: err => this.error = this.getErrorMessage(err)
    })
  }
}
