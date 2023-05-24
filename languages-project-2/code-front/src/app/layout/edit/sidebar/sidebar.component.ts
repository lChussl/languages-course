import { Component } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent {
  public taxonType: string | undefined;
  public loaded: boolean = false;

  constructor(private router: Router) { }

  ngOnInit() {
    this.router.events.subscribe(event => {
      if(!this.loaded) {
        const arr = this.router.url.split('/');
        this.taxonType = arr[arr.length - 1];
      } else {
        if (event instanceof NavigationEnd) {
          const arr = event.url.split('/');
          this.taxonType = arr[arr.length - 1];
          this.loaded = true;
        }
      }
    });
  }

  /**
   * Checks if the current url have a taxon type
   *
   * @return true or false
   */
  public isTaxon() {
    const arr = ['kingdom', 'division', 'class', 'order', 'family', 'genus', 'species'];

    return arr.includes(<string>this.taxonType);
  }
}
