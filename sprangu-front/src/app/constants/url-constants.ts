import {HttpHeaders} from "@angular/common/http";

export class UrlConstants {
  private static backEndUrl = 'http://localhost:8080/api';

  // endpoints
  private static userEndpoint = this.backEndUrl + '/user'
  public static toolsEndpoint = this.backEndUrl + '/tools';

  // endpoints methods
  //user
  public static registerUser = this.userEndpoint + '/register';
  public static loginUser = this.userEndpoint + '/login';
  public static refresh = this.userEndpoint + '/refresh';

  //tools
  public static myTools = this.toolsEndpoint + '/my-tools'


  // headers for api calls
  public static headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Access-Control-Allow-Headers': 'Content-Type',
    'Access-Control-Allow-Origin': 'http://localhost:4200',
    'Access-Control-Allow-Credentials': 'true',
    'Access-Control-Expose-Headers': 'Authorization'
  });
}
