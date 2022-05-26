import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { ToolForRental } from 'src/app/domain/tools/toolForRental.model';
import { ToolsService } from 'src/app/services/tools.service';
import {ToolsFilter} from "../../domain/tools/toolsFilter.model";
import { FormBuilder } from '@angular/forms';
import {ToolTypeConstants} from "../../constants/tool-type-constants";

@Component({
  selector: 'app-tools-for-rental',
  templateUrl: './tools-for-rental.component.html',
  styleUrls: ['./tools-for-rental.component.css']
})
export class ToolsForRentalComponent implements OnInit {
  model = new ToolsFilter();
  ToolTypeConstants = ToolTypeConstants;
  public tools: ToolForRental[];
  
  constructor(
    private router: Router,
    private toolService: ToolsService) { 
    }

  async ngOnInit(): Promise<void> {
    this.toolService.getAll().subscribe(result => {
      this.tools = result
    });
  }
  toolTypeKeys: any;

  toolsList: ToolForRental[];
  isInvalid: boolean;

  submit(): void {
    if (this.model.minHourlyPrice > this.model.maxHourlyPrice || this.model.minDailyPrice > this.model.maxDailyPrice){
      this.isInvalid = true;
    }
    if(this.isInvalid){
      return;
    }
    
    if (this.model.toolType==""){
      this.model.toolType=null
    }
    console.log(this.model.toolType)

    this.toolService.searchTools(this.model).subscribe( result =>{
      this.tools = result;
      console.log(result)
    })
    console.log(this.model)
  }

  clear(){
    console.log("resetting")
    if (this.model.toolType==""){
      this.model.toolType=null
    }
    this.toolService.getAll().subscribe(result => {
      this.tools = result
    });
    this.isInvalid = false;
  }
}
