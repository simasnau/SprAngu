import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {ToolForRental} from 'src/app/domain/tools/toolForRental.model';
import {ToolsService} from 'src/app/services/tools.service';
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

  public tool: ToolForRental = new ToolForRental();
  isOwner: boolean;
  isCurrentLoaner: boolean;
  rentEndDate: Date;
  imagesList: any[] = [];

  constructor(
    private route: ActivatedRoute,
    private toolsService: ToolsService,
    public authService: AuthenticationService,
    public matDialog: MatDialog) {
  }

  async ngOnInit(): Promise<void> {
    let id = this.route.snapshot.params['id'];
    this.getTool(id);
  }

  private getTool(id: number) {
    this.toolsService.get(id).subscribe(result => {
      this.tool = result
      const loggedUserId = this.authService.user.id;
      this.isOwner = loggedUserId === this.tool.owner.id;
      this.isCurrentLoaner = loggedUserId && this.tool.currentUser?.id ? loggedUserId === this.tool.currentUser?.id : false;
      this.rentEndDate = new Date(this.tool.rentEndDate)
      this.resolveImages();
    });
  }

  getRentButtonMessage(): string {
    if (this.isOwner) {
      return "Jūs esate šio įrankio savininkas";
    }
    if (!this.authService.user.id) {
      return "Prisijunkite";
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
          text += " Vėluojate grąžinti įrankį " + response.daysLate + " dieną/s. Jums buvo pritaikytas padidintas mokestis";
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

  private resolveImages(): void {
    this.imagesList = [];
    this.tool.imageContent.forEach(image => {
      this.imagesList.push({image: image, thumbImage: image});
    })
  }

  expandImage($event: number): void {
    this.imagesList = [];
    //todo fix when there is time x) (pulls but doesnt update images)
    this.toolsService.getFullImages(this.tool.id).subscribe(
      images => images.forEach(image => this.imagesList.push({image: image, thumbImage: image}))
    );
  }
}
