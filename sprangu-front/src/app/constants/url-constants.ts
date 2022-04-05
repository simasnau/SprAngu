import {HttpHeaders} from "@angular/common/http";

export class UrlConstants {
  private static backEndUrl = 'http://localhost:8080';

  // endpoints
  private static userEndpoint = this.backEndUrl + '/user'

  // endpoints methods
  public static registerUser = this.userEndpoint + '/register';


  // headers for api calls
  private static headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Access-Control-Allow-Origin': '*',
    'Access-Control-Allow-Headers': 'Content-Type'
  });
  public static options = {
    headers: this.headers
  }
}
