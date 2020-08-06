import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../services/api.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
  providers: []
})
export class DashboardComponent implements OnInit {

  displayHidden: boolean;
  hiddenContent: string;

  constructor(private api: ApiService) { }

  ngOnInit(): void {
    this.displayHidden = false;
    this.hiddenContent = null;
  }

  onToggleDisplayHidden(): void {
    this.displayHidden = !this.displayHidden;

    if (this.displayHidden) {

      this.api.getHiddenData().subscribe(result => this.hiddenContent = result);
      
    } else {
      this.hiddenContent = null;
    }
  }
}
