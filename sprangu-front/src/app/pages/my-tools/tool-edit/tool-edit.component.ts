import {Component, OnInit} from '@angular/core';
import {ToolsService} from "../../../services/tools.service";
import {ActivatedRoute} from "@angular/router";
import {ToolForRental} from "../../../domain/tools/toolForRental.model";
import {ToolTypeConstants} from "../../../constants/tool-type-constants";

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
    console.log(this.model)

    this.toolsService.updateToolDescription(this.model).subscribe(
      (e) => {
        console.log("Res: ", e);
        // window.location.reload()
      }
    );

    // await new Promise(r => setTimeout(r, 5000));

    // this.model.hourlyPrice += 100;
    // this.toolsService.updateToolDescription(this.model).subscribe(
    //   (e) => {
    //     console.log("Res: ", e);
    //   }
    // );
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
