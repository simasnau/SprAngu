import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ToolTypeConstants } from 'src/app/constants/tool-type-constants';
import { ToolForRental } from 'src/app/domain/tools/toolForRental.model';
import { ToolsService } from 'src/app/services/tools.service';

@Component({
  selector: 'app-create-tool',
  templateUrl: './create-tool.component.html',
  styleUrls: ['./create-tool.component.css']
})
export class CreateToolComponent implements OnInit {

  ToolTypeConstants = ToolTypeConstants;
  
  model = new ToolForRental();

  constructor(
    private toolsService: ToolsService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {}

  submit(form: any): void {
    if (form.invalid) {
      return;
    }
    console.log(this.model)
    this.toolsService.createTool(this.model).subscribe(
      () => window.location.reload()
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
  

}
