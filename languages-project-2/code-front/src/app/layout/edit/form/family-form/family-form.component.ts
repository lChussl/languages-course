import { Component, EventEmitter, Input, Output, SimpleChanges } from '@angular/core';
import { AbstractControl, FormBuilder, Validators } from "@angular/forms";
import { EditService } from "../../../../services/edit.service";
import { ToastrService } from "ngx-toastr";

@Component({
  selector: 'app-family-form',
  templateUrl: './family-form.component.html',
  styleUrls: ['./family-form.component.css']
})
export class FamilyFormComponent {
  @Input() action: string = 'Create';
  @Input() families: Array<any> = [];
  @Input() orders: Array<any> = [];
  @Output() loadTaxons = new EventEmitter();

  public itemsPerPage = 5;
  public currentPage = 1;
  public selectedItem: string | undefined;
  public isDisabled: boolean = true;

  public familyForm = this.formBuilder.group({
    id: [''],
    scientificName: ['', [Validators.required, FamilyFormComponent.validateName]],
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
    if(this.familyForm.valid && this.action === 'Create') {
      this.editService.createTaxon('family', <string>this.familyForm.get('scientificName')!.value, <string>this.familyForm.controls['author'].value, <string>this.familyForm.controls['publicationYear'].value, <string>this.familyForm.controls['ancestorId'].value).subscribe(() => {
        this.familyForm.reset();
        this.toast.success('Family successfully created');
        this.loadTaxons.emit();
      }, error => {
        this.toast.warning('Something went wrong');
        console.error('Error:', error);
      });
    } else if(this.familyForm.valid && this.action === 'Update') {
      this.editService.updateTaxon('family', <string>this.familyForm.get('id')!.value, <string>this.familyForm.get('scientificName')!.value, <string>this.familyForm.controls['author'].value, <string>this.familyForm.controls['publicationYear'].value, <string>this.familyForm.controls['ancestorId'].value).subscribe(() => {
        this.familyForm.reset();
        this.resetForm();
        this.toast.success('Family successfully updated');
        this.loadTaxons.emit();
      }, error => {
        this.toast.warning('Something went wrong');
      });
    }
  }

  public deleteTaxon(scientificName: string) {
    this.editService.deleteTaxon('family', scientificName).subscribe(() => {
      this.toast.success('Family successfully deleted');
      this.loadTaxons.emit();
    }, error => {
      this.toast.warning('Something went wrong');
      this.loadTaxons.emit();
    });
  }

  public resetForm() {
    this.familyForm.reset();
    this.selectedItem = undefined;

    if(this.action === 'Update') {
      this.familyForm.controls['id'].disable();
      this.familyForm.controls['scientificName'].disable();
      this.familyForm.controls['author'].disable();
      this.familyForm.controls['publicationYear'].disable();
      this.isDisabled = true;
    } else {
      this.familyForm.controls['id'].enable();
      this.familyForm.controls['scientificName'].enable();
      this.familyForm.controls['author'].enable();
      this.familyForm.controls['publicationYear'].enable();
    }
  }

  public selectTaxon(id: string, scientificName: string, author: string, publicationYear: string, ancestorId: string) {
    this.selectedItem = scientificName;

    this.familyForm.controls['id'].enable();
    this.familyForm.controls['scientificName'].enable();
    this.familyForm.controls['author'].enable();
    this.familyForm.controls['publicationYear'].enable();
    this.isDisabled = false;

    this.familyForm.controls['id'].setValue(id);
    this.familyForm.controls['scientificName'].setValue(scientificName);
    this.familyForm.controls['author'].setValue(author);
    this.familyForm.controls['publicationYear'].setValue(publicationYear);
    this.familyForm.controls['ancestorId'].setValue(ancestorId);
    this.familyForm.controls['ancestorIdName'].setValue(this.orders.find(order => order.id ===  ancestorId).scientificName);
  }

  public get totalPages(): number {
    return Math.ceil(this.families.length / this.itemsPerPage);
  }

  public get paginatedItems(): any[] {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    const endIndex = startIndex + this.itemsPerPage;
    return this.families.slice(startIndex, endIndex);
  }

  public changePage(newPage: number): void {
    if (newPage >= 1 && newPage <= this.totalPages) {
      this.currentPage = newPage;
    }
  }

  static validateName(control: AbstractControl) {
    if(control.value && (control.value.charAt(0).toUpperCase() !== control.value.charAt(0) || !(control.value.endsWith('eae')))) {
      return {invalid: true};
    } return null;
  }

  selectAncestor(id: string, scientificName: string) {
    if(id && scientificName) {
      this.familyForm.get('ancestorId')?.setValue(id);
      this.familyForm.get('ancestorIdName')?.setValue(scientificName);
    }
  }
}
