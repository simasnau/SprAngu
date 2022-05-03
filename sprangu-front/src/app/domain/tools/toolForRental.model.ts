import { UserInfo } from "../user/model/user-info";

export class ToolForRental {
    id: number;
    name: string;
    image: string;
    type: string;

    cost: {
        daily: number;
        hourly: number;
    };

    shortDescription: string;
    description: string;

    owner: UserInfo;
    currentUser: UserInfo;
  }