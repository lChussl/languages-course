import { Component, OnInit } from '@angular/core';
import { EditService } from "../../../services/edit.service";
import {ImageService} from "../../../services/image.service";
import {ToastrService} from "ngx-toastr";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-single-image',
  templateUrl: './single-image.component.html',
  styleUrls: ['./single-image.component.css']
})
export class SingleImageComponent implements OnInit{
  public id: number = 0;
  public image: Array<any> = [];
  public description: string = '';
  public url: string = '';
  public date: string = '';
  public license: string = '';
  public author: string = '';
  public owner: string = '';
  public keywords: Array<any> = [];
  public owners: Array<any> = [];
  public taxons: Array<any> = [];

  constructor(private imageService: ImageService, private toast: ToastrService, private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.params.subscribe((params) => {
      this.id = params['id'];
    });

    this.getSingleImageData();
  }

  /**
   * Retrieves the single image id
   */
  public getSingleImageData() {
    this.imageService.getSingleImageData(this.id).subscribe(data => {
      this.image = data['image'];
      this.description = data['image']['description'];
      this.url = data['image']['url'];
      this.license = data['image']['license'];
      this.date = this.formattedDate(data['image']['date']);
      this.keywords = data['image']['keywords'];
      this.taxons = data['image']['taxons'];
      this.owners = data['owner'];

      this.author = this.owners.find(owner => owner.id === data['image']['author']).name;
      this.owner = this.owners.find(owner => owner.id === data['image']['owner']).name;


      this.toast.success('Image with id ' + this.id + ' successfully loaded');
    }, error => {
      this.toast.warning('Something went wrong while loading the image');
    })
  }

  /**
   * Formats date to yyyy/mm/dd
   *
   * @param date
   */
  public formattedDate(date: string) {
    const inputDate = new Date(date);

    const month = inputDate.getMonth() + 1; // Months are zero-based in JavaScript
    const day = inputDate.getDate();
    const year = inputDate.getFullYear();

    return `${year}-${month.toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}`;
  }
}
