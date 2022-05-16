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
      console.log(result)
      this.model = result;
      console.log(this.model);
    });
  }

  submit(form: any): void {
    if (form.invalid) {
      return;
    }
    console.log(this.model)
    this.toolsService.updateToolDescription(this.model).subscribe(
      () => window.location.reload()
    );
  }

  processFile(imageInput: HTMLInputElement): void {
    if (!imageInput.files) {
      return;
    }
    if (!this.model.images) {
      this.model.images = []
    }
    for (let i = 0; i < imageInput.files.length; i++) {
      let reader = new FileReader();
      reader.readAsDataURL(imageInput.files[i]);
      reader.onload = (event) => {
        // @ts-ignore
        this.model.images.push(<String>event.target.result);
      }
    }
  }

  removeFile(i: number): void {
    this.model.images.splice(i, 1);
  }
}
