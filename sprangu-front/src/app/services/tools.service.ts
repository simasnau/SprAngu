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
  private DUMMY_DATA = [
    {id: 1, image: "asdfasdf", name: "Wrench", cost: {daily: 30, hourly: 5}, user: "HILTI", shortDescription: "Tobulas plaktukas", description: ""} as ToolForRental,
    {id: 2, image: "asdfasdf", name: "Hammer", cost: {daily: 30, hourly: 5}, user: "jonas", shortDescription: "Tobulas plaktukas", description: ""} as ToolForRental,
    {id: 3, image: "asdfasdf", name: "Generator", cost: {daily: 30, hourly: 5}, user: "jonas", shortDescription: "Tobulas plaktukas", description: ""} as ToolForRental,
    {id: 4, image: "asdfasdf", name: "Wrench", cost: {daily: 30, hourly: 5}, user: "HILTI", shortDescription: "Tobulas plaktukas", description: ""} as ToolForRental,
    {id: 5, image: "asdfasdf", name: "Hammer", cost: {daily: 30, hourly: 5}, user: "jonas", shortDescription: "Tobulas plaktukas", description: ""} as ToolForRental,
    {id: 6, image: "asdfasdf", name: "Generator", cost: {daily: 30, hourly: 5}, user: "HILTI", shortDescription: "Tobulas plaktukas", description: ""} as ToolForRental,
  ];

  constructor() { 
  }

  async getAll() : Promise<ToolForRental[]> {
    // const res = await fetch(this.apiUrl + '/all');
    // const apiTools = await res.json();
    const apiTools : any = [];

    // return apiTools.map(this.toolMapper);
    return this.DUMMY_DATA;
  }

  async get(id: number) : Promise<ToolForRental> {
    return this.DUMMY_DATA.find(tool => tool.id = id) as ToolForRental;
  }

  toolMapper(item : any) {
    const tool = new ToolForRental();
    tool.id = item.id;
    tool.name = item.name;
    return tool; 
  }
}
