import {Component, OnInit} from '@angular/core';
import {ToolTypeConstants} from 'src/app/constants/tool-type-constants';
import {ToolsService} from 'src/app/services/tools.service';
import {ToolBasicDto} from "../../domain/tools/tool-basic-dto";
import {AuthenticationService} from "../../services/authentication.service";
import {DialogComponent} from "../../components/dialog/dialog.component";
import {DialogConstants} from "../../constants/dialog-constants";
import {MatDialog} from "@angular/material/dialog";
import {Router} from "@angular/router";

@Component({
  selector: 'app-create-tool',
  templateUrl: './create-tool.component.html',
  styleUrls: ['./create-tool.component.css']
})
export class CreateToolComponent implements OnInit {

  ToolTypeConstants = ToolTypeConstants;

  model = new ToolBasicDto();

  constructor(
    private toolsService: ToolsService,
    public matDialog: MatDialog,
    private authenticationService: AuthenticationService,
    private router: Router
  ) {
  }

  ngOnInit(): void {
  }

  submit(form: any): void {
    if (form.invalid) {
      return;
    }
    this.model.ownerId = this.authenticationService.user.id;
    this.toolsService.createTool(this.model).subscribe(
      () => this.openSuccess(),
      error => this.openError()
    );
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


  private openSuccess() {
    const dialogRef = this.matDialog.open(DialogComponent, {
      width: '25%',
      data: DialogConstants.TOOL_CREATE_SUCCESS
    });

    dialogRef.afterClosed().subscribe(() => {
      this.router.navigate(['/my-tools/view']);
    });
  }

  private openError() {
    const dialogRef = this.matDialog.open(DialogComponent, {
      width: '25%',
      data: DialogConstants.TOOL_CREATE_ERROR
    });

    dialogRef.afterClosed().subscribe(() => {
      window.location.reload()
    });
  }
}
