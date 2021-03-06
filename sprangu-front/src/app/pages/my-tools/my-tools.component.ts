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

  removeTool(id: number): void {
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
            this.openSuccessDialog();
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

  async hideTool(id: number): Promise<void> {
    // @ts-ignore
    this.tools.find(tool => tool.id === id).visible = await this.toolService.hideTool(id);
  }

  private openSuccessDialog(): void {
    const dialogRef = this.matDialog.open(DialogComponent, {
      width: '25%',
      data: DialogConstants.REMOVE_TOOL_SUCCESS
    });
  }
}
