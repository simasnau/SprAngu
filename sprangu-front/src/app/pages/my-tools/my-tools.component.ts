import {Component, OnInit} from '@angular/core';
import {ToolsService} from "../../services/tools.service";
import {ToolBasicDto} from "../../domain/tools/tool-basic-dto";
import {MatDialog} from "@angular/material/dialog";
import {DialogComponent} from "../../components/dialog/dialog.component";
import {DialogConstants} from "../../constants/dialog-constants";

@Component({
  selector: 'app-my-tools',
  templateUrl: './my-tools.component.html',
  styleUrls: ['./my-tools.component.css']
})
export class MyToolsComponent implements OnInit {

  tools: ToolBasicDto[] = [];

  constructor(
    private toolService: ToolsService,
    public matDialog: MatDialog
  ) {
  }

  ngOnInit(): void {
    this.getTools();
  }

  private getTools() {
    this.toolService.getUserToolsShortView().subscribe(result => this.tools = result);
  }

  openDeleteDialog(id: number): void {
    const dialogRef = this.matDialog.open(DialogComponent, {
      width: '25%',
      data: DialogConstants.REMOVE_TOOL_ADVERTISEMENT
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.toolService.deleteToolFromMyTools(id).subscribe(result => {
          if (!result) {
            this.openErrorDialog();
          } else {
            this.getTools();
          }
        });
      }
    });
  }

  private openErrorDialog(): void {
    const dialogRef = this.matDialog.open(DialogComponent, {
      width: '25%',
      data: DialogConstants.REMOVE_TOOL_ERROR
    });
  }

  hideTool(id: number): void {
    // @ts-ignore
    this.toolService.hideTool(id).subscribe(result => this.tools.find(tool => tool.id === id).visible = result);
  }
}
