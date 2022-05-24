import {Component, OnInit} from '@angular/core';
import {ToolsService} from "../../../services/tools.service";
import {ActivatedRoute} from "@angular/router";
import {ToolForRental} from "../../../domain/tools/toolForRental.model";
import {ToolTypeConstants} from "../../../constants/tool-type-constants";
import { DialogComponent } from 'src/app/components/dialog/dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { DialogConstants } from 'src/app/constants/dialog-constants';

@Component({
  selector: 'app-tool-edit',
  templateUrl: './tool-edit.component.html',
  styleUrls: ['./tool-edit.component.css']
})
export class ToolEditComponent implements OnInit {

  ToolTypeConstants = ToolTypeConstants;

  model = new ToolForRental();

  constructor(
    private toolsService: ToolsService,
    public matDialog: MatDialog,
    private route: ActivatedRoute
  ) {
  }

  ngOnInit(): void {
    this.toolsService.get(Number(this.route.snapshot.paramMap.get('id'))).subscribe(result => {
      this.model = result;
      console.log(this.model);
    });
  }

  async submit(form: any): Promise<void> {
    if (form.invalid) {
      return;
    }

    this.toolsService.updateToolDescription(this.model).subscribe({
      complete: () => window.location.reload(),
      error: (e) => {
        if (e.status === 409)
          this.openVersionMismatchDialog();
      }
    });
  }

  private openVersionMismatchDialog(): void {
    const dialogRef = this.matDialog.open(DialogComponent, {
      width: '25%',
      data: DialogConstants.TOOL_UPDATE_OLE_ERROR
    });

    dialogRef.afterClosed().subscribe(() => {
      window.location.reload()
    });
  }

  processFile(imageInput: HTMLInputElement): void {
    if (!imageInput.files) {
      return;
    }
    if (!this.model.imageContent) {
      this.model.imageContent = []
    }
    for (let i = 0; i < imageInput.files.length; i++) {
      let reader = new FileReader();
      reader.readAsDataURL(imageInput.files[i]);
      reader.onload = (event) => {
        // @ts-ignore
        this.model.imageContent.push(<String>event.target.result);
      }
    }
  }

  removeFile(i: number): void {
    this.model.imageContent.splice(i, 1);
  }
}
