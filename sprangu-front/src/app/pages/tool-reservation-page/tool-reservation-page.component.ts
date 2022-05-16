import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { DialogComponent } from 'src/app/components/dialog/dialog.component';
import { DialogConstants } from 'src/app/constants/dialog-constants';
import { ToolForRental } from 'src/app/domain/tools/toolForRental.model';
import { ToolsService } from 'src/app/services/tools.service';
import {AuthenticationService} from "../../services/authentication.service";
import {RentStartDto} from "../../domain/tools/rent-start-dto";
import { Location } from '@angular/common'

@Component({
  selector: 'app-tool-reservation-page',
  templateUrl: './tool-reservation-page.component.html',
  styleUrls: ['./tool-reservation-page.component.css']
})
export class ToolReservationPageComponent implements OnInit {

  public tool : ToolForRental = new ToolForRental();
  date: any;
  time = {hour: 12, minute: 0};
  overallDuration = {days: 0, hours: 0};
  overallCost: number;
  private MS_PER_HOUR = 1000 * 60 * 60;

  constructor(
    private route: ActivatedRoute,
    private toolsService: ToolsService,
    private authenticationService: AuthenticationService,
    private location: Location,
    public matDialog: MatDialog) { }

  async ngOnInit(): Promise<void> {
    const id = this.route.snapshot.params['id'];
    this.toolsService.get(id).subscribe(result => this.tool=result);
  }

  dateChanged () {
    if (!this.dateIsValid()) {
      this.overallDuration.days = 0;
      this.overallDuration.hours = 0;
      this.overallCost = 0;
      return;
    }

    const dateUntil = this.getEndDate();
    const diff = this.dateDiffInHours(new Date(), dateUntil) + 1;

    this.overallDuration.days = Math.floor(diff/24);
    this.overallDuration.hours = diff - this.overallDuration.days * 24;

    this.overallCost = this.calculateTotalPrice(dateUntil);
  }

  dateIsValid(): boolean {
    try {
      const dateUntil = this.getEndDate();
      const diff = this.dateDiffInHours(new Date(), dateUntil);

      return diff >= 0;
    } catch (error) {
      return false;
    }
  }

  dateDiffInHours(a: Date, b: Date) {
    const utc1 = Date.UTC(a.getFullYear(), a.getMonth(), a.getDate(), a.getHours(), a.getMinutes());
    const utc2 = Date.UTC(b.getFullYear(), b.getMonth(), b.getDate(), b.getHours(), b.getMinutes());

    return Math.floor((utc2 - utc1) / this.MS_PER_HOUR);
  }

  calculateTotalPrice(endDate: Date) {
    const rentHours = this.dateDiffInHours(new Date(), endDate) + 1;
    if (rentHours < 24) {
      return rentHours * this.tool.hourlyPrice;
    }

    return Math.ceil(rentHours / 24) * this.tool.dailyPrice;
}

  async rentTool() {
    if(!this.dateIsValid()) {
      return;
    }

    const request = new RentStartDto();
    request.userId = this.authenticationService.user.id;
    request.rentEndDate = this.getEndDate();

    this.toolsService.rentTool(this.tool.id, request).subscribe(result => {
      if (!result) {
        this.matDialog.open(DialogComponent, {
          width: '25%',
          data: DialogConstants.TOOL_RENT_ERROR
        });
      } else {
        this.matDialog.open(DialogComponent, {
          width: '25%',
          data: DialogConstants.TOOL_RENT_SUCCES
        }).afterClosed().subscribe(() => this.location.back());

      }
    });

  }

  private getEndDate() {
    return new Date(this.date.year, this.date.month - 1, this.date.day, this.time.hour, this.time.minute);
  }
}
