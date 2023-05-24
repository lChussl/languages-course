import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {
  constructor(private router: Router) { }

  /**
   * Sends the search value to the images component
   *
   * @param searchValue
   */
  public onSearch(searchValue: string): void {
    this.router.navigate(['images'], { queryParams: { search: searchValue } });
  }
}
