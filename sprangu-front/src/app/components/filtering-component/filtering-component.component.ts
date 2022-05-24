import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import {ToolTypeConstants} from "../../constants/tool-type-constants";
import {ToolForRental} from "../../domain/tools/toolForRental.model";
import {ToolsService} from "../../services/tools.service";
import {ActivatedRoute} from "@angular/router";
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {ToolsFilter} from "../../domain/tools/toolsFilter.model";

@Component({
  selector: 'app-filtering-component',
  templateUrl: './filtering-component.component.html',
  styleUrls: ['./filtering-component.component.css']
})

export class FilteringComponentComponent implements OnInit {
  model = new ToolsFilter();
  ToolTypeConstants = ToolTypeConstants;

  constructor(
    private toolsService: ToolsService,
    private route: ActivatedRoute,
    private formBuilder: FormBuilder
    ) { }

  // filterForm = this.formBuilder.group({
  //   raktazodis: '',
  //   minDienosKaina: 0,
  //   maxDienosKaina: 0,
  //   minValandosKaina: 0,
  //   maxValandosKaina: 0,
  //   ToolTypeConstants: null
  // });

  toolTypeKeys: any;

  toolsList: ToolForRental[];

  ngOnInit(): void {
  }

  //@Output() searchEvent: EventEmitter<any> = new EventEmitter();

  submit(form: any): void {
    console.log(this.model)
    this.toolsService.searchTools(this.model);
  }

  clear(form: any): void {
    console.log(this.model)
    this.toolsService.getAll();
  }

  reloadPage() {
    window.location.reload();
   }
}
