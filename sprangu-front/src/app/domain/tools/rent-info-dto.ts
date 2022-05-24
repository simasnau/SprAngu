import {UserInfo} from "../user/model/user-info";

export class ToolRentInfoDto {
  id: number;
  toolId: number;
  toolName: string;
  toolDescription: string;
  toolType: string;
  imageContent: string[];
  owner: UserInfo;
  userName: string;
  rentStartDate: Date;
  originalEndDate: Date;
  realEndDate: Date;
  hourlyPrice: number;
  dailyPrice: number;
}
