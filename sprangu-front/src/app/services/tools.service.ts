import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ToolForRental} from "../domain/tools/toolForRental.model";
import {firstValueFrom, Observable} from "rxjs";
import {UrlConstants} from "../constants/url-constants";
import {AuthenticationService} from "./authentication.service";
import {ToolBasicDto} from "../domain/tools/tool-basic-dto";

@Injectable({
  providedIn: 'root'
})
export class ToolsService {

  UrlConstants = UrlConstants;
  private DUMMY_DATA = [
    {id: 1, image: "asdfasdf", name: "Wrench", cost: {daily: 30, hourly: 5}, user: "HILTI", shortDescription: "Tobulas plaktukas", description: ""} as ToolForRental,
    {id: 2, image: "asdfasdf", name: "Hammer", cost: {daily: 30, hourly: 5}, user: "jonas", shortDescription: "Tobulas plaktukas", description: ""} as ToolForRental,
    {id: 3, image: "asdfasdf", name: "Generator", cost: {daily: 30, hourly: 5}, user: "jonas", shortDescription: "Tobulas plaktukas", description: ""} as ToolForRental,
    {id: 4, image: "asdfasdf", name: "Wrench", cost: {daily: 30, hourly: 5}, user: "HILTI", shortDescription: "Tobulas plaktukas", description: ""} as ToolForRental,
    {id: 5, image: "asdfasdf", name: "Hammer", cost: {daily: 30, hourly: 5}, user: "jonas", shortDescription: "Tobulas plaktukas", description: ""} as ToolForRental,
    {id: 6, image: "asdfasdf", name: "Generator", cost: {daily: 30, hourly: 5}, user: "HILTI", shortDescription: "Tobulas plaktukas", description: ""} as ToolForRental,
  ];

  constructor(
    private httpClient: HttpClient,
    private authenticationService: AuthenticationService
  ) {
  }

  async getAll(): Promise<ToolForRental[]> {
    // const res = await fetch(this.apiUrl + '/all');
    // const apiTools = await res.json();
    const apiTools: any = [];

    // return apiTools.map(this.toolMapper);
    return this.DUMMY_DATA;
  }

  async get(id: number): Promise<ToolForRental> {
    return this.DUMMY_DATA.find(tool => tool.id = id) as ToolForRental;
  }

  getUserToolsShortView(): Observable<ToolBasicDto[]> {
    const url = UrlConstants.myTools + '/' + this.authenticationService.user.id;
    return this.httpClient.get<ToolBasicDto[]>(url);
  }

  toolMapper(item: any) {
    const tool = new ToolForRental();
    tool.id = item.id;
    tool.name = item.name;
    return tool;
  }

  deleteToolFromMyTools(toolId: number): Observable<boolean> {
    const url = UrlConstants.myTools + '/' + toolId + '/delete';
    return this.httpClient.delete<boolean>(url);
  }

  async hideTool(toolId: number): Promise<boolean> {
    const url = UrlConstants.myTools + '/' + toolId + '/edit-visibility';
    return firstValueFrom(this.httpClient.patch<boolean>(url, {}));
  }
}
