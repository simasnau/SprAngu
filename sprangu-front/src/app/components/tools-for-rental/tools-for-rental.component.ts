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

  submit(form: any): void {
    
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

  clear(form: any): void {
    console.log(this.model)
    this.toolService.getAll();
  }

  reloadPage() {
    window.location.reload();
   }

}
