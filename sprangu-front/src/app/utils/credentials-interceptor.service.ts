import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AuthenticationService} from "../services/authentication.service";
import {JwtConstants} from "../constants/jwt-constants";
import {CookieService} from "ngx-cookie-service";

@Injectable()
export class CredentialsInterceptor implements HttpInterceptor {

  constructor(
    private authenticationService: AuthenticationService,
    private cookieService: CookieService
  ) {
  }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    console.log('intersepting, expired:', this.authenticationService.isJwtExpired());
    console.log(request)
    const httpRequest = request.clone({
      withCredentials: true,
      setHeaders: {
        'Authorization': `${this.cookieService.get(JwtConstants.JWT)}`,
      }
    });
    console.log(httpRequest)
    // if(!this.cookieService.get(JwtConstants.JWT) && this.authenticationService.isJwtExpired()){
    //   console.log('is expired and trying to get new')
    //   this.authenticationService.refreshTokens().subscribe(response => {
    //     this.cookieService.set(JwtConstants.JWT, response.jwtToken);
    //     this.cookieService.set(JwtConstants.REFRESH, response.jwtRefreshToken);
    //     this.authenticationService.resolveUser();
    //     return next.handle(httpRequest);
    //   });
    // }
    // console.log('not expired');
    return next.handle(httpRequest);
  }
}
