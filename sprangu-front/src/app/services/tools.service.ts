import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ToolForRental} from "../domain/tools/toolForRental.model";
import {ToolsFilter} from "../domain/tools/toolsFilter.model";
import {BehaviorSubject, firstValueFrom, Observable, Subject} from "rxjs";
import {UrlConstants} from "../constants/url-constants";
import {AuthenticationService} from "./authentication.service";
import {ToolBasicDto} from "../domain/tools/tool-basic-dto";
import {RentStartDto} from "../domain/tools/rent-start-dto";
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import {RentEndDto} from "../domain/tools/rent-end-dto";
import {ToolRentInfoDto} from "../domain/tools/rent-info-dto";

@Injectable({
  providedIn: 'root'
})
export class ToolsService {

  UrlConstants = UrlConstants;

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

  searchTools(model: ToolsFilter): Observable<ToolForRental[]>{
      return this.httpClient.post<ToolForRental[]>(UrlConstants.toolsEndpoint+ "/search", model);
  }
  
  updateToolDescription(model: ToolForRental): Observable<ToolForRental> {
    return this.httpClient.put<ToolForRental>(UrlConstants.toolsEndpoint + "/update", model);
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


  getFullImages(id: number): Observable<String[]> {
    return this.httpClient.get<String[]>(UrlConstants.toolsEndpoint + "/" + id + "/full-images");
  }

  getForEdit(id: number) {
    return this.httpClient.get<ToolForRental>(UrlConstants.toolsEndpoint + '/' + id + "/edit");
  }
}
