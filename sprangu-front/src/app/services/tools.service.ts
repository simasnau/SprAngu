import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {UserInfo} from "../domain/user/model/user-info";
import {ToolForRental} from "../domain/tools/toolForRental.model";
import {Observable} from "rxjs";
import {UrlConstants} from "../constants/url-constants";

@Injectable({
  providedIn: 'root'
})
export class ToolsService {

  private apiUrl: string = UrlConstants.tools;

  constructor() { 
  }

  async getAll() : Promise<ToolForRental[]> {
    // const res = await fetch(this.apiUrl + '/all');
    // const apiTools = await res.json();
    const apiTools : any = [];

    // return apiTools.map(this.toolMapper);

    // DUMMY DATA
    return [
      {id: 1, image: "asdfasdf", name: "Wrench", cost: {daily: 30, hourly: 5}, user: "HILTI"} as ToolForRental,
      {id: 2, image: "asdfasdf", name: "Hammer", cost: {daily: 30, hourly: 5}, user: "jonas"} as ToolForRental,
      {id: 3, image: "asdfasdf", name: "Generator", cost: {daily: 30, hourly: 5}, user: "jonas"} as ToolForRental,
      {id: 1, image: "asdfasdf", name: "Wrench", cost: {daily: 30, hourly: 5}, user: "HILTI"} as ToolForRental,
      {id: 2, image: "asdfasdf", name: "Hammer", cost: {daily: 30, hourly: 5}, user: "jonas"} as ToolForRental,
      {id: 3, image: "asdfasdf", name: "Generator", cost: {daily: 30, hourly: 5}, user: "HILTI"} as ToolForRental,
    ]
  }

  toolMapper(item : any) {
    const tool = new ToolForRental();
    tool.id = item.id;
    tool.name = item.name;
    return tool; 
  }
}
