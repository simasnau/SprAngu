import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ToolForRental } from 'src/app/domain/tools/toolForRental.model';
import { ToolsService } from 'src/app/services/tools.service';

@Component({
  selector: 'app-tool-details-page',
  templateUrl: './tool-details-page.component.html',
  styleUrls: ['./tool-details-page.component.css']
})
export class ToolDetailsPageComponent implements OnInit {

  public tool : ToolForRental = new ToolForRental();

  constructor(private route: ActivatedRoute, private toolsService: ToolsService) { }

  async ngOnInit(): Promise<void> {
    let id = this.route.snapshot.params['id'];
    this.toolsService.get(id).subscribe(result => this.tool = result);
  }

}
