import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {EditService} from "../../../services/edit.service";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css']
})
export class FormComponent implements OnInit{
  public taxonType: string | undefined;
  public title: string | undefined;
  public kingdoms: Array<any> = [];
  public divisions: Array<any> = [];
  public classes: Array<any> = [];
  public orders: Array<any> = [];
  public families: Array<any> = [];
  public genuses: Array<any> = [];
  public species: Array<any> = [];
  public action: string = 'Create';

  constructor(private route: ActivatedRoute, private editService: EditService, private toast: ToastrService) {
    this.getTaxonData();
  }

  ngOnInit() {
    this.route.params.subscribe((params) => {
      this.taxonType = params['taxonType'];
      this.title = this.taxonType &&  this.taxonType.charAt(0).toUpperCase() + this.taxonType.slice(1);
    });
  }

  /**
   * Retrieves the taxons from the backend
   */
  public getTaxonData() {
    this.editService.getData().subscribe(data => {
      this.kingdoms = data['kingdoms'];
      this.divisions = data['divisions'];
      this.classes = data['classes'];
      this.orders = data['orders'];
      this.families = data['families'];
      this.genuses = data['genuses'];
      this.species = data['species'];
      console.log(data);
    }, error => {
      this.toast.warning('Something went wrong while retrieving the data');
    });
  }

  /**
   * Change the action (update, create or delete)
   *
   * @param type
   */
  public changeAction(type: string) {
    this.action = type;
  }
}
