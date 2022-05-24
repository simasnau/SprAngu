import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { ToolForRental } from 'src/app/domain/tools/toolForRental.model';
import { ToolsService } from 'src/app/services/tools.service';
import {ToolsFilter} from "../../domain/tools/toolsFilter.model";

@Component({
  selector: 'app-tools-for-rental',
  templateUrl: './tools-for-rental.component.html',
  styleUrls: ['./tools-for-rental.component.css']
})
export class ToolsForRentalComponent implements OnInit {

  public tools: ToolForRental[];
  
  constructor(
    private router: Router,
    private toolService: ToolsService) { 
    }

  async ngOnInit(): Promise<void> {
    this.toolService.getAll().subscribe(result => {
      this.tools = result
    });
    this.toolService.searchEvent.subscribe((toolsList:ToolForRental[])=>{
        this.tools = toolsList
    })
  }

}
