import { UserInfo } from "../user/model/user-info";

export class ToolForRental {
    id: number;
    name: string;
    description: string;
    owner: UserInfo;
    currentUser: UserInfo;
    currentRentId: number;
    rentEndDate: Date
    hourlyPrice: number;
    dailyPrice:number;
    toolType: string;
    imageContent: String[] = [];
  }
