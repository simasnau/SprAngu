import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {UserInfo} from "../domain/user/model/user-info";
import {Observable} from "rxjs";
import {UrlConstants} from "../constants/url-constants";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(
    private http: HttpClient
  ) {
  }

  createUser(userInfo: UserInfo): Observable<void> {
    return this.http.post<void>(UrlConstants.registerUser, userInfo, UrlConstants.options);
  }

  login(model: UserInfo): Observable<UserInfo> {
    let params = new HttpParams();
    params = params.set('name', model.name);
    params = params.set('password', model.password);
    return this.http.get<UserInfo>(UrlConstants.loginUser, {params});
  }
}
