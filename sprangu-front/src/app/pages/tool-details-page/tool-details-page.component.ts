import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {ToolForRental} from 'src/app/domain/tools/toolForRental.model';
import {ToolsService} from 'src/app/services/tools.service';

@Component({
  selector: 'app-tool-details-page',
  templateUrl: './tool-details-page.component.html',
  styleUrls: ['./tool-details-page.component.css']
})
export class ToolDetailsPageComponent implements OnInit {

  public tool: ToolForRental = new ToolForRental();
  imagesList: any[] = [];

  constructor(private route: ActivatedRoute, private toolsService: ToolsService) {
  }

  async ngOnInit(): Promise<void> {
    let id = this.route.snapshot.params['id'];
    this.toolsService.get(id).subscribe(result => {
      this.tool = result;
      this.resolveImages();
    });
  }

  private resolveImages(): void {
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
