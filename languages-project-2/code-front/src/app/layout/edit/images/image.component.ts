import {Component, OnInit, SimpleChanges} from '@angular/core';
import { EditService } from '../../../services/edit.service';
import {FormBuilder, Validators} from "@angular/forms";
import {NgxDropzoneChangeEvent} from "ngx-dropzone";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-images',
  templateUrl: './image.component.html',
  styleUrls: ['./images.component.css']
})
export class ImagesComponent implements OnInit {
  public action: string = 'Create';
  public files: File[] = [];
  public taxons: Array<any> = [];
  public licenses: Array<any> = [];
  public keywords: Array<any> = [];
  public images: Array<any> = [];
  public owners: Array<any> = [];
  public authors: Array<any> = [];
  public selectedAuthor = '';
  public selectedOwner = '';
  public isDisabled: boolean = false;
  public selectedItem: string | undefined;
  public itemsPerPage = 5;
  public currentPage = 1;

  public imageForm = this.formBuilder.group({
    id: [''],
    description: ['', [Validators.required, Validators.maxLength(500)]],
    url: ['', [Validators.required]],
    date: ['', [Validators.required]],
    author: ['', [Validators.required]],
    owner: ['', [Validators.required]],
    keywords: ['', [Validators.required]],
    license: ['', [Validators.required]],
    taxons: ['', [Validators.required]]
  });

  constructor(private editService: EditService, private formBuilder: FormBuilder, private toast: ToastrService) { }

  ngOnInit() {
    this.getImageData();
  }

  /**
   * Gets the necessary data for the form
   */
  public getImageData() {
    this.editService.getData().subscribe(data => {
      for(const key in data) {
        if (Array.isArray(data[key])) {
          this.taxons.push(...data[key]);
        }
      }

      console.log(this.taxons);
    });

    this.editService.getImageData().subscribe(data => {
      this.keywords = data['keywords'];
      this.licenses = data['licenses'];
      this.images = data['images'];
      this.owners = data['owners'];
      this.authors = this.owners.filter(owner => owner.ownerType === 'person')

      console.log(this.keywords);
      console.log(this.licenses);
      console.log(this.authors);
      console.log(data);
    });
  }

  /**
   * Sends the needed information to add a new image
   */
  public addImage() {
    if(Array.isArray(this.imageForm.controls['keywords'].value) && Array.isArray(this.imageForm.controls['taxons'].value)) {
      const keywordsString = this.imageForm.controls['keywords'].value.join(',');
      const taxonsString = this.imageForm.controls['taxons'].value.join(',');
      this.editService.uploadFile(this.files[0]).subscribe(result => {
        this.imageForm.controls['url'].setValue(result["filePath"]);
        this.toast.success('Image successfully uploaded');
        this.editService.addImage(<string> this.imageForm.controls['description'].value, <string> this.imageForm.controls['url'].value, <string> this.imageForm.controls['date'].value, <string> this.imageForm.controls['author'].value, <string> this.imageForm.controls['owner'].value, keywordsString, <string> this.imageForm.controls['license'].value, taxonsString).subscribe(() => {
          this.getImageData();
          this.imageForm.reset();
          this.selectedOwner = '';
          this.selectedAuthor = '';
          this.files = [];
          this.toast.success('Image successfully created')
        }, error => {
          this.toast.warning("Something went wrong while trying to create the image");
        });
      }, error => {
        this.toast.warning("Something went wrong while trying to upload the image");
      });
    }
  }

  /**
   * Change action (create, update or delete)
   *
   * @param type
   */
  public changeAction(type: string) {
    this.action = type;
    this.resetForm();
  }

  /**
   * Change the status of the form depending on the action selected
   */
  public resetForm() {
    this.imageForm.reset();
    this.selectedItem = undefined;
    this.selectedOwner = '';
    this.selectedAuthor = '';

    if(this.action === 'Update') {
      this.imageForm.controls['description'].disable();
      this.imageForm.controls['url'].disable();
      this.imageForm.controls['author'].disable();
      this.imageForm.controls['date'].disable();
      this.imageForm.controls['owner'].disable();
      this.imageForm.controls['keywords'].disable();
      this.imageForm.controls['license'].disable();
      this.imageForm.controls['taxons'].disable();
      this.isDisabled = true;
    } else {
      this.enableForm();
    }
  }

  /**
   * Selects the image for update form
   *
   * @param description
   * @param url
   * @param date
   * @param license
   * @param taxons
   * @param keyword
   * @param author
   * @param ownerId
   * @param id
   */
  public selectImage(description: string, url: string, date: string, license: string, taxons: Array<any>, keyword: Array<any>, author: string, ownerId: string, id: string) {
    this.selectedItem = description;
    const taxonsId = taxons.map(taxon => taxon.id);

    const formattedDate = this.formattedDate(date);

    this.enableForm();

    this.imageForm.controls['id'].setValue(id);
    this.imageForm.controls['description'].setValue(description);
    this.imageForm.controls['url'].setValue(url);
    this.imageForm.controls['date'].setValue(formattedDate);
    this.selectAuthor(author, this.owners.find(owner => owner.id === author).name);
    this.selectOwner(ownerId, this.owners.find(owner => owner.id === ownerId).name);

    this.selectLicense(license);
    // @ts-ignore
    this.imageForm.controls['taxons'].setValue(taxonsId);
    // @ts-ignore
    this.imageForm.controls['keywords'].setValue(keyword);
  }

  /**
   * Enables  the form and set isDisable to false
   */
  public enableForm() {
    this.imageForm.controls['description'].enable();
    this.imageForm.controls['url'].enable();
    this.imageForm.controls['author'].enable();
    this.imageForm.controls['date'].enable();
    this.imageForm.controls['owner'].enable();
    this.imageForm.controls['keywords'].enable();
    this.imageForm.controls['license'].enable();
    this.imageForm.controls['taxons'].enable();
    this.isDisabled = false;
  }

  /**
   * Saves the uploaded file to a list
   *
   * @param event
   */
  public onSelect(event: NgxDropzoneChangeEvent) {
    if (event.addedFiles.length > 0) {
      const file = event.addedFiles[0];
      if (file.type === 'image/jpeg' || file.type === 'image/png') {
        this.files = [];
        this.files.push(file);
        this.imageForm.controls['url'].setValue('uploaded');
      } else {
        console.error('Invalid file type: only JPEG and PNG images are allowed');
      }
    } else {
      const file = event.addedFiles[0];
      if (file.type === 'image/jpeg' || file.type === 'image/png') {
        this.files.push(file);
      } else {
        console.error('Invalid file type: only JPEG and PNG images are allowed');
      }
    }
  }

  /**
   * Remove the file uploaded
   *
   * @param event
   */
  public onRemove(event: File) {
    this.files.splice(this.files.indexOf(event), 1);
    this.imageForm.controls['url'].setValue('');
  }

  /**
   * Select Author from dropdown
   *
   * @param id
   * @param name
   */
  public selectAuthor(id: string, name: string) {
    this.imageForm.controls['author'].setValue(id);
    this.selectedAuthor = name;
  }

  /**
   * Select Owner from dropdown
   *
   * @param id
   * @param name
   */
  public selectOwner(id: string, name: string) {
    this.imageForm.controls['owner'].setValue(id);
    this.selectedOwner = name;
  }

  /**
   * Select License from dropdown
   *
   * @param license
   */
  public selectLicense(license: string) {
    this.imageForm.controls['license'].setValue(license);
  }

  /**
   * Methods for delete paginator
   */
  public get totalPages(): number {
    return Math.ceil(this.images.length / this.itemsPerPage);
  }

  public get paginatedItems(): any[] {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    const endIndex = startIndex + this.itemsPerPage;
    return this.images.slice(startIndex, endIndex);
  }

  public changePage(newPage: number): void {
    if (newPage >= 1 && newPage <= this.totalPages) {
      this.currentPage = newPage;
    }
  }

  /**
   * Updates the image
   */
  public updateImage(): void {
    if(Array.isArray(this.imageForm.controls['keywords'].value) && Array.isArray(this.imageForm.controls['taxons'].value)) {
      const keywordsString = this.imageForm.controls['keywords'].value.join(',');
      const taxonsString = this.imageForm.controls['taxons'].value.join(',');
      if(this.files[0]) {
        this.editService.uploadFile(this.files[0]).subscribe(result => {
          this.imageForm.controls['url'].setValue(result["filePath"]);
          this.toast.success('Image successfully uploaded');
          this.editService.updateImage(<string> this.imageForm.controls['id'].value, <string> this.imageForm.controls['description'].value, <string> this.imageForm.controls['url'].value, <string> this.imageForm.controls['date'].value, <string> this.imageForm.controls['author'].value, <string> this.imageForm.controls['owner'].value, keywordsString, <string> this.imageForm.controls['license'].value, taxonsString).subscribe(() => {
            this.getImageData();
            this.imageForm.reset();
            this.selectedOwner = '';
            this.selectedAuthor = '';
            this.selectedItem = '';
            this.files = [];
            this.toast.success('Image successfully created')
          }, error => {
            this.toast.warning("Something went wrong while trying to create the image");
          });
        }, error => {
          this.toast.warning("Something went wrong while trying to upload the image");
        });
      } else {
        this.editService.updateImage(<string> this.imageForm.controls['id'].value, <string> this.imageForm.controls['description'].value, <string> this.imageForm.controls['url'].value, <string> this.imageForm.controls['date'].value, <string> this.imageForm.controls['author'].value, <string> this.imageForm.controls['owner'].value, keywordsString, <string> this.imageForm.controls['license'].value, taxonsString).subscribe(() => {
          this.getImageData();
          this.imageForm.reset();
          this.selectedOwner = '';
          this.selectedAuthor = '';
          this.selectedItem = '';
          this.files = [];
          this.toast.success('Image successfully created')
        }, error => {
          this.toast.warning("Something went wrong while trying to create the image");
        });
      }
    }
  }

  /**
   * Deletes an image by id
   *
   * @param id
   */
  public deleteImage(id: number): void {
    this.editService.deleteImage(id).subscribe(() => {
        this.toast.success('Image succesfully deleted');
        this.getImageData();
      }, (error) => {
        this.toast.warning('Something went wrong while trying to delete the image');
      }
    );
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


