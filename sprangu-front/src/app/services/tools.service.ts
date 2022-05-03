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

  constructor(
    private httpClient: HttpClient,
    private authenticationService: AuthenticationService
  ) {
  }

  async getAll(): Promise<ToolForRental[]> {
    const res = await fetch(UrlConstants.toolsEndpoint + '/all');
    const apiTools = await res.json();

    return apiTools.map(this.toolMapper);
  }

  async get(id: number): Promise<ToolForRental> {
    const res = await fetch(UrlConstants.toolsEndpoint + '/' + id);
    const apiTool = await res.json();

    return this.toolMapper(apiTool);
  }

  getUserToolsShortView(): Observable<ToolBasicDto[]> {
    const url = UrlConstants.myTools + '/' + this.authenticationService.user.id;
    return this.httpClient.get<ToolBasicDto[]>(url, {headers: UrlConstants.headers});
  }

  toolMapper(item: any): ToolForRental {
    return {
      id: item.id,
      name: item.name,
      cost: {
          daily: item.dailyPrice,
          hourly: item.hourlyPrice,
        },
      type: item.toolType,
      image: item.imageContent,
      description: item.description,
      shortDescription: item.description,
      owner: item.owner,
      currentUser: item.currentUser,
    }
  }

  deleteToolFromMyTools(toolId: number): Observable<boolean> {
    const url = UrlConstants.myTools + '/' + toolId + '/delete';
    return this.httpClient.delete<boolean>(url);
  }

  async hideTool(toolId: number): Promise<boolean> {
    const url = UrlConstants.myTools + '/' + toolId + '/edit-visibility';
    return firstValueFrom(this.httpClient.patch<boolean>(url, {}));
  }

  async rentTool(toolId: number, userId: number, endDate: Date) {
    return fetch(UrlConstants.toolsEndpoint + '/' + toolId + '/rent', {
      method: "post",
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        userId: userId,
        rentEndDate: endDate.toISOString(),
      })
    })
  }
}
