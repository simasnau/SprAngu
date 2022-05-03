import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { DialogComponent } from 'src/app/components/dialog/dialog.component';
import { DialogConstants } from 'src/app/constants/dialog-constants';
import { ToolForRental } from 'src/app/domain/tools/toolForRental.model';
import { ToolsService } from 'src/app/services/tools.service';

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
  private MS_PER_DAY = 1000 * 60 * 60 * 24;
  private MS_PER_HOUR = 1000 * 60 * 60;

  constructor(
    private route: ActivatedRoute,
    private toolsService: ToolsService,
    public matDialog: MatDialog) { }

  async ngOnInit(): Promise<void> {
    const id = this.route.snapshot.params['id'];
    this.tool = await this.toolsService.get(id);
  }

  dateChanged () {
    if (!this.dateIsValid()) {
      this.overallDuration.days = 0;
      this.overallDuration.hours = 0;
      this.overallCost = 0;
      return;
    }
    
    const dateUntil = new Date(this.date.year, this.date.month-1, this.date.day, this.time.hour, this.time.minute);
    const diff = this.dateDiffInHours(new Date(), dateUntil);
    
    this.overallDuration.days = Math.floor(diff/24);
    this.overallDuration.hours = diff - this.overallDuration.days * 24;

    this.overallCost = this.overallDuration.days * this.tool.cost.daily + this.overallDuration.hours * this.tool.cost.hourly;    
  }

  dateIsValid(): Boolean {
    try {
      const dateUntil = new Date(this.date.year, this.date.month-1, this.date.day, this.time.hour, this.time.minute);
      const diff = this.dateDiffInHours(new Date(), dateUntil);

      return diff > 0;
    } catch (error) {
      return false;
    }
  }
 
  dateDiffInHours(a: Date, b: Date) {
    const utc1 = Date.UTC(a.getFullYear(), a.getMonth(), a.getDate(), a.getHours(), a.getMinutes());
    const utc2 = Date.UTC(b.getFullYear(), b.getMonth(), b.getDate(), b.getHours(), b.getMinutes());

    return Math.floor((utc2 - utc1) / this.MS_PER_HOUR);
  }

  async rentTool() {
    if(!this.dateIsValid()) {
      return;
    }

    const endDate = new Date(this.date.year, this.date.month-1, this.date.day, this.time.hour, this.time.minute);
    const res = await this.toolsService.rentTool(this.tool.id, 1, endDate);
    
    if (!res.ok) {
      const dialogRef = this.matDialog.open(DialogComponent, {
        width: '25%',
        data: DialogConstants.TOOL_RENT_ERROR
      });
    }
    
  }
}