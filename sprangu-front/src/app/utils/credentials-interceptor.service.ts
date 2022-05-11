import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest, HttpResponse} from '@angular/common/http';
import {Observable, tap} from 'rxjs';
import {AuthenticationService} from "../services/authentication.service";
import {JwtConstants} from "../constants/jwt-constants";
import {CookieService} from "ngx-cookie-service";
import {UrlConstants} from "../constants/url-constants";

@Injectable()
export class CredentialsInterceptor implements HttpInterceptor {

  constructor(
    private authenticationService: AuthenticationService,
    private cookieService: CookieService
  ) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let httpRequest = request.clone({
      headers: UrlConstants.headers,
      withCredentials: true,
    });
    if (!this.cookieService.get(JwtConstants.JWT)) {
      return next.handle(request)
    }
    httpRequest = httpRequest.clone({
      setHeaders: {
        'Authorization': `${this.cookieService.get(JwtConstants.JWT)}`,
      }
    });
    return next.handle(httpRequest).pipe(tap((httpEvent: HttpEvent<any>) => {
      if (httpEvent instanceof HttpResponse) {
        let headers: HttpHeaders = httpEvent.headers;
        const jwt = headers.get(JwtConstants.AUTHORIZATION);
        if (jwt) {
          this.cookieService.set(JwtConstants.JWT, jwt, {
              path: '/'
            }
          )
        }
      }
    }));
  }
}
