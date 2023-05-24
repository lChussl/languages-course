import {Component, EventEmitter, Input, OnInit, Output, SimpleChanges } from '@angular/core';
import {AbstractControl, FormBuilder, Validators} from "@angular/forms";
import {EditService} from "../../../../services/edit.service";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-division-form',
  templateUrl: './division-form.component.html',
  styleUrls: ['./division-form.component.css']
})
export class DivisionFormComponent implements OnInit{
  @Input() action: string = 'Create';
  @Input() divisions: Array<any> = [];
  @Input() kingdoms: Array<any> = [];
  @Output() loadTaxons = new EventEmitter();

  public itemsPerPage = 5;
  public currentPage = 1;
  public selectedItem: string | undefined;
  public isDisabled: boolean = true;

  public divisionForm = this.formBuilder.group({
    id: [''],
    scientificName: ['', [Validators.required, DivisionFormComponent.validateName]],
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
    if(this.divisionForm.valid && this.action === 'Create') {
      this.editService.createTaxon('division', <string>this.divisionForm.get('scientificName')!.value, <string>this.divisionForm.controls['author'].value, <string>this.divisionForm.controls['publicationYear'].value, <string>this.divisionForm.controls['ancestorId'].value).subscribe(() => {
        this.divisionForm.reset();
        this.toast.success('Division successfully created');
        this.loadTaxons.emit();
      }, error => {
        this.toast.warning('Something went wrong');
        console.error('Error:', error);
      });
    } else if(this.divisionForm.valid && this.action === 'Update') {
      this.editService.updateTaxon('division', <string>this.divisionForm.get('id')!.value, <string>this.divisionForm.get('scientificName')!.value, <string>this.divisionForm.controls['author'].value, <string>this.divisionForm.controls['publicationYear'].value, <string>this.divisionForm.controls['ancestorId'].value).subscribe(() => {
        this.divisionForm.reset();
        this.resetForm();
        this.toast.success('Division successfully updated');
        this.loadTaxons.emit();
      }, error => {
        this.toast.warning('Something went wrong');
      });
    }
  }

  public deleteTaxon(scientificName: string) {
    this.editService.deleteTaxon('division', scientificName).subscribe(() => {
      this.toast.success('Division successfully deleted');
      this.loadTaxons.emit();
    }, error => {
      this.toast.warning('Something went wrong');
      this.loadTaxons.emit();
    });
  }

  public resetForm() {
    this.divisionForm.reset();
    this.selectedItem = undefined;

    if(this.action === 'Update') {
      this.divisionForm.controls['id'].disable();
      this.divisionForm.controls['scientificName'].disable();
      this.divisionForm.controls['author'].disable();
      this.divisionForm.controls['publicationYear'].disable();
      this.isDisabled = true;
    } else {
      this.divisionForm.controls['id'].enable();
      this.divisionForm.controls['scientificName'].enable();
      this.divisionForm.controls['author'].enable();
      this.divisionForm.controls['publicationYear'].enable();
    }
  }

  public selectTaxon(id: string, scientificName: string, author: string, publicationYear: string, ancestorId: string) {
    this.selectedItem = scientificName;

    this.divisionForm.controls['id'].enable();
    this.divisionForm.controls['scientificName'].enable();
    this.divisionForm.controls['author'].enable();
    this.divisionForm.controls['publicationYear'].enable();
    this.isDisabled = false;

    this.divisionForm.controls['id'].setValue(id);
    this.divisionForm.controls['scientificName'].setValue(scientificName);
    this.divisionForm.controls['author'].setValue(author);
    this.divisionForm.controls['publicationYear'].setValue(publicationYear);
    this.divisionForm.controls['ancestorId'].setValue(ancestorId);
    this.divisionForm.controls['ancestorIdName'].setValue(this.kingdoms.find(kingdom => kingdom.id ===  ancestorId).scientificName);
  }

  public get totalPages(): number {
    return Math.ceil(this.divisions.length / this.itemsPerPage);
  }

  public get paginatedItems(): any[] {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    const endIndex = startIndex + this.itemsPerPage;
    return this.divisions.slice(startIndex, endIndex);
  }

  public changePage(newPage: number): void {
    if (newPage >= 1 && newPage <= this.totalPages) {
      this.currentPage = newPage;
    }
  }

  static validateName(control: AbstractControl) {
    if(control.value && (control.value.charAt(0).toUpperCase() !== control.value.charAt(0) || !(control.value.endsWith('phyta')))) {
      return {invalid: true};
    } return null;
  }

  selectAncestor(id: string, scientificName: string) {
    if(id && scientificName) {
      this.divisionForm.get('ancestorId')?.setValue(id);
      this.divisionForm.get('ancestorIdName')?.setValue(scientificName);
    }
  }
}
