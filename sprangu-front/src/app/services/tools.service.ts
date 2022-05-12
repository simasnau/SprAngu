import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ToolForRental} from "../domain/tools/toolForRental.model";
import {firstValueFrom, Observable} from "rxjs";
import {UrlConstants} from "../constants/url-constants";
import {AuthenticationService} from "./authentication.service";
import {ToolBasicDto} from "../domain/tools/tool-basic-dto";
import {RentStartDto} from "../domain/tools/rent-start-dto";

@Injectable({
  providedIn: 'root'
})
export class ToolsService {

  constructor(
    private httpClient: HttpClient,
    private authenticationService: AuthenticationService
  ) {
  }

  getAll(): Observable<ToolForRental[]> {
    return this.httpClient.get<ToolForRental[]>(UrlConstants.toolsEndpoint + '/all');
  }

  get(id: number): Observable<ToolForRental> {
    return this.httpClient.get<ToolForRental>(UrlConstants.toolsEndpoint + '/' + id);
  }

  getUserToolsShortView(): Observable<ToolBasicDto[]> {
    const url = UrlConstants.myTools + '/' + this.authenticationService.user.id;
    return this.httpClient.get<ToolBasicDto[]>(url, {headers: UrlConstants.headers});
  }

  deleteToolFromMyTools(toolId: number): Observable<boolean> {
    const url = UrlConstants.myTools + '/' + toolId + '/delete';
    return this.httpClient.delete<boolean>(url);
  }

  async hideTool(toolId: number): Promise<boolean> {
    const url = UrlConstants.myTools + '/' + toolId + '/edit-visibility';
    return firstValueFrom(this.httpClient.patch<boolean>(url, {}));
  }

  rentTool(toolId: number, request: RentStartDto): Observable<boolean> {
    return this.httpClient.post<boolean>(UrlConstants.toolsEndpoint + '/' + toolId + '/rent', request);
  }
}
