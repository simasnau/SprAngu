import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ToolForRental } from 'src/app/domain/tools/toolForRental.model';
import { ToolsService } from 'src/app/services/tools.service';
import {AuthenticationService} from "../../services/authentication.service";
import {DialogComponent} from "../../components/dialog/dialog.component";
import {DialogConstants, DialogData, DialogRelevance} from "../../constants/dialog-constants";
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: 'app-tool-details-page',
  templateUrl: './tool-details-page.component.html',
  styleUrls: ['./tool-details-page.component.css']
})
export class ToolDetailsPageComponent implements OnInit {

  public tool : ToolForRental = new ToolForRental();
  isOwner: boolean;
  isCurrentLoaner: boolean;
  rentEndDate: Date;

  constructor(private route: ActivatedRoute,
              private toolsService: ToolsService,
              private authService: AuthenticationService,
              public matDialog: MatDialog) { }

  async ngOnInit(): Promise<void> {
    let id = this.route.snapshot.params['id'];
    this.getTool(id);
  }

  private getTool(id: number) {
    this.toolsService.get(id).subscribe(result => {
      this.tool = result
      const loggedUserId = this.authService.user.id;
      this.isOwner = loggedUserId === this.tool.owner.id;
      this.isCurrentLoaner = loggedUserId === this.tool.currentUser?.id
      this.rentEndDate = new Date(this.tool.rentEndDate)
    });
  }

  getRentButtonMessage(): string {
    if (this.isOwner) {
      return "Jūs esate šio įrankio savininkas";
    }

    return "Nuomotis";
  }

  disableRentButton(): boolean {
    return this.isOwner || this.tool.currentUser != null;
  }

  returnTool() {
    const dialogRef = this.matDialog.open(DialogComponent, {
      width: '25%',
      data: DialogConstants.TOOL_RETURN_PROMPT
    });

    dialogRef.afterClosed().subscribe(result => {
      if (!result) {
        return;
      }

      this.toolsService.returnTool(this.tool.currentRentId).subscribe(response => {
        if (response === null) {
          this.openDialog(DialogConstants.TOOL_RETURN_ERROR);
          return;
        }

        var text = 'Įrankis sėkmingai grąžintas. Kaina: ' + response.totalPrice + ".";
        if (response.daysLate > 0) {
          text += " Vėluojate grąžinti įrankį " + response.daysLate + " dienas. Jums buvo pritaikytas padidintas mokestis";
        } else {
          text += " Įrankį gražinote laiku";
        }

        const dialogData = new DialogData(text, '', 'Apmokėti', DialogRelevance.SUCCESS);
        this.openDialog(dialogData);
        this.getTool(this.tool.id);

      });
    });
  }

  private openDialog(dialogData: DialogData): void {
    this.matDialog.open(DialogComponent, {
      width: '25%',
      data: dialogData
    });
  }

}
