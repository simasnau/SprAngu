import {Component, OnInit} from "@angular/core";
import {ToolsService} from "../../../services/tools.service";
import {MatDialog} from "@angular/material/dialog";
import {ToolRentInfoDto} from "../../../domain/tools/rent-info-dto";


@Component({
  selector: 'app-rented-tools',
  templateUrl: './rented-tools-component.html',
  styleUrls: ['./rented-tools.component.css']
})
export class RentedToolsComponent implements OnInit {

  rentedToolsInfo: ToolRentInfoDto[] = [];

  constructor(
    private toolService: ToolsService,
    public matDialog: MatDialog
  ) {
  }

  ngOnInit(): void {
    this.getMyRentedTools();
  }

  private getMyRentedTools() {
    this.toolService.getMyRentedTools().subscribe(result => this.rentedToolsInfo = result);
  }

}
