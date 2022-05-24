import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ToolForRental} from "../domain/tools/toolForRental.model";
import {firstValueFrom, Observable} from "rxjs";
import {UrlConstants} from "../constants/url-constants";
import {AuthenticationService} from "./authentication.service";
import {ToolBasicDto} from "../domain/tools/tool-basic-dto";
import {RentStartDto} from "../domain/tools/rent-start-dto";
import {RentEndDto} from "../domain/tools/rent-end-dto";
import {ToolRentInfoDto} from "../domain/tools/rent-info-dto";

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
    return this.httpClient.get<ToolBasicDto[]>(UrlConstants.myTools, {headers: UrlConstants.headers});
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

  updateToolDescription(model: ToolForRental): Observable<void> {
    return this.httpClient.put<void>(UrlConstants.toolsEndpoint + "/update", model);
  }

  createTool(model: ToolForRental): Observable<void> {
    return this.httpClient.put<void>(UrlConstants.toolsEndpoint + "/create", model);
  }

  returnTool(currentRentId: number): Observable<RentEndDto> {
    return this.httpClient.get<RentEndDto>(UrlConstants.toolsEndpoint + '/rent/stop/' + currentRentId)
  }

  getMyRentedTools(): Observable<ToolRentInfoDto[]> {
    return this.httpClient.get<ToolRentInfoDto[]>(UrlConstants.toolsEndpoint + '/my-rented-tools')
  }

}
