import {Injectable} from '@angular/core';
import {UserDetailed, UserInfo} from "../domain/user/model/user-info";
import {AppConstants} from "../constants/app-constants";
import {Router} from "@angular/router";
import {Observable} from "rxjs";
import {UrlConstants} from "../constants/url-constants";
import {HttpClient, HttpParams} from "@angular/common/http";
import {JwtResponse} from "../domain/jwt/JwtResponse";
import {JwtConstants} from "../constants/jwt-constants";
import {CookieService} from "ngx-cookie-service";
import jwt_decode from 'jwt-decode';
import {JwtHelperService} from "@auth0/angular-jwt";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  user = new UserInfo();
  jwtHelper = new JwtHelperService();

  constructor(
    private router: Router,
    private http: HttpClient,
    private cookieService: CookieService,
  ) {
  }

  isLogged(): boolean {
    return this.user.id > 0;
  }

  logout(): void {
    this.user = new UserInfo();
    this.cookieService.delete(JwtConstants.JWT)
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

  createUser(userInfo: UserInfo): Observable<void> {
    return this.http.post<void>(UrlConstants.registerUser, userInfo, UrlConstants.options);
  }

  login(model: UserInfo): Observable<string> {
    let params = new HttpParams();
    params = params.append('name', model.name);
    params = params.append('password', model.password);
    return this.http.get(UrlConstants.loginUser, {
      params: params,
      headers: UrlConstants.headers,
      responseType: 'text'
    });
  }

  resolveUser(): void {
    const userDetailed = this.getDecodedToken(this.cookieService.get(JwtConstants.JWT));
    console.log('user detailed', userDetailed);
    this.user.id = userDetailed.id;
    this.user.name = userDetailed.sub;
    this.user.role = userDetailed.ROLE;
    console.log('user after', this.user)
    // console.log('user service', this.tokenExpired(this.cookieService.get(JwtConstants.JWT)))
  }

  getDecodedToken(token: string): UserDetailed {
    try {
      return jwt_decode(token);
    } catch (Error) {
      return new UserDetailed();
    }
  }

  isJwtExpired(): boolean {
    return this.jwtHelper.isTokenExpired(this.cookieService.get(JwtConstants.JWT));
  }

  refreshTokens(): Observable<JwtResponse> {
    return this.http.get<JwtResponse>(UrlConstants.refresh);
  }
}
