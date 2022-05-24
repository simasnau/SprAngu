import {ToolTypeConstants} from "../../constants/tool-type-constants";

export class ToolsFilter{
    name: string;
    minHourlyPrice: number;
    maxHourlyPrice: number;
    minDailyPrice: number;
    maxDailyPrice: number;
    toolType: ToolTypeConstants;
}