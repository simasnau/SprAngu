import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {UserInfo} from "./model/user-info";
import {Observable} from "rxjs";
import {UrlConstants} from "../../constants/url-constants";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(
    private http: HttpClient
  ) {
  }

  createUser(userInfo: UserInfo): Observable<void> {
    console.log(UrlConstants.registerUser)
    return this.http.post<void>(UrlConstants.registerUser, userInfo, UrlConstants.options);
  }
}
