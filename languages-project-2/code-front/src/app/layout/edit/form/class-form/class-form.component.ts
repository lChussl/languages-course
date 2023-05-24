import {Component, EventEmitter, Input, Output, SimpleChanges} from '@angular/core';
import {AbstractControl, FormBuilder, Validators} from "@angular/forms";
import {EditService} from "../../../../services/edit.service";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-class-form',
  templateUrl: './class-form.component.html',
  styleUrls: ['./class-form.component.css']
})
export class ClassFormComponent {
  @Input() action: string = 'Create';
  @Input() divisions: Array<any> = [];
  @Input() classes: Array<any> = [];
  @Output() loadTaxons = new EventEmitter();

  public itemsPerPage = 5;
  public currentPage = 1;
  public selectedItem: string | undefined;
  public isDisabled: boolean = true;

  public classForm = this.formBuilder.group({
    id: [''],
    scientificName: ['', [Validators.required, ClassFormComponent.validateName]],
    author: ['', [Validators.required]],
    publicationYear: ['', [Validators.required]],
    ancestorId: ['', [Validators.required]],
    ancestorIdName: ['']
  });

  constructor(private formBuilder: FormBuilder, private editService: EditService, private toast: ToastrService) { }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['action'] && changes['action'].currentValue) {
      this.resetForm();
    }
  }

  ngOnInit() {}

  public submitForm() {
    if(this.classForm.valid && this.action === 'Create') {
      this.editService.createTaxon('class', <string>this.classForm.get('scientificName')!.value, <string>this.classForm.controls['author'].value, <string>this.classForm.controls['publicationYear'].value, <string>this.classForm.controls['ancestorId'].value).subscribe(() => {
        this.classForm.reset();
        this.toast.success('Class successfully created');
        this.loadTaxons.emit();
      }, error => {
        this.toast.warning('Something went wrong');
      });
    } else if(this.classForm.valid && this.action === 'Update') {
      this.editService.updateTaxon('class', <string>this.classForm.get('id')!.value, <string>this.classForm.get('scientificName')!.value, <string>this.classForm.controls['author'].value, <string>this.classForm.controls['publicationYear'].value, <string>this.classForm.controls['ancestorId'].value).subscribe(() => {
        this.classForm.reset();
        this.resetForm();
        this.toast.success('Class successfully updated');
        this.loadTaxons.emit();
      }, error => {
        this.toast.warning('Something went wrong');
      });
    }
  }

  public deleteTaxon(scientificName: string) {
    this.editService.deleteTaxon('class', scientificName).subscribe(() => {
      this.toast.success('Class successfully deleted');
      this.loadTaxons.emit();
    }, error => {
      this.toast.warning('Something went wrong');
      this.loadTaxons.emit();
    });
  }

  public resetForm() {
    this.classForm.reset();
    this.selectedItem = undefined;

    if(this.action === 'Update') {
      this.classForm.controls['id'].disable();
      this.classForm.controls['scientificName'].disable();
      this.classForm.controls['author'].disable();
      this.classForm.controls['publicationYear'].disable();
      this.isDisabled = true;
    } else {
      this.classForm.controls['id'].enable();
      this.classForm.controls['scientificName'].enable();
      this.classForm.controls['author'].enable();
      this.classForm.controls['publicationYear'].enable();
    }
  }

  public selectTaxon(id: string, scientificName: string, author: string, publicationYear: string, ancestorId: string) {
    this.selectedItem = scientificName;

    this.classForm.controls['id'].enable();
    this.classForm.controls['scientificName'].enable();
    this.classForm.controls['author'].enable();
    this.classForm.controls['publicationYear'].enable();
    this.isDisabled = false;

    this.classForm.controls['id'].setValue(id);
    this.classForm.controls['scientificName'].setValue(scientificName);
    this.classForm.controls['author'].setValue(author);
    this.classForm.controls['publicationYear'].setValue(publicationYear);
    this.classForm.controls['ancestorId'].setValue(ancestorId);
    this.classForm.controls['ancestorIdName'].setValue(this.divisions.find(division => division.id ===  ancestorId).scientificName);
  }

  public get totalPages(): number {
    return Math.ceil(this.classes.length / this.itemsPerPage);
  }

  public get paginatedItems(): any[] {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    const endIndex = startIndex + this.itemsPerPage;
    return this.classes.slice(startIndex, endIndex);
  }

  public changePage(newPage: number): void {
    if (newPage >= 1 && newPage <= this.totalPages) {
      this.currentPage = newPage;
    }
  }

  static validateName(control: AbstractControl) {
    if(control.value && (control.value.charAt(0).toUpperCase() !== control.value.charAt(0) || !(control.value.endsWith('opsida')))) {
      return {invalid: true};
    } return null;
  }

  selectAncestor(id: string, scientificName: string) {
    if(id && scientificName) {
      this.classForm.get('ancestorId')?.setValue(id);
      this.classForm.get('ancestorIdName')?.setValue(scientificName);
    }
  }
}
