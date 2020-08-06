import { Component, OnInit, Input } from '@angular/core';
import { HiddenDataService } from '../../services/hidden-data.service';
import { TokenService } from '../../services/token.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  displayHidden: boolean;
  hiddenContent: string;

  constructor(
    private router: Router,
    private hiddenDataService: HiddenDataService,
    private tokenService: TokenService) { }

  ngOnInit(): void {
    this.displayHidden = false;
    this.hiddenContent = null;
  }

  onToggleDisplayHidden(): void {
    this.displayHidden = !this.displayHidden;

    if (this.displayHidden) {
      let loginResult = this.tokenService.getLoginResult();

      this.hiddenDataService.getHiddenData(loginResult.data, loginResult.token).subscribe(
        result => {
          this.hiddenContent = result;
        },
        error => {
          this.router.navigateByUrl('login');
        }
      );
    } else {
      this.hiddenContent = null;
    }
  }
}
