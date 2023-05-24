import {Component, EventEmitter, Input, Output, SimpleChanges} from '@angular/core';
import {AbstractControl, FormBuilder, Validators} from "@angular/forms";
import {EditService} from "../../../../services/edit.service";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-genus-form',
  templateUrl: './genus-form.component.html',
  styleUrls: ['./genus-form.component.css']
})
export class GenusFormComponent {
  @Input() action: string = 'Create';
  @Input() genuses: Array<any> = [];
  @Input() families: Array<any> = [];
  @Output() loadTaxons = new EventEmitter();

  public itemsPerPage = 5;
  public currentPage = 1;
  public selectedItem: string | undefined;
  public isDisabled: boolean = true;

  public genusForm = this.formBuilder.group({
    id: [''],
    scientificName: ['', [Validators.required, GenusFormComponent.validateName]],
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
    if(this.genusForm.valid && this.action === 'Create') {
      this.editService.createTaxon('genus', <string>this.genusForm.get('scientificName')!.value, <string>this.genusForm.controls['author'].value, <string>this.genusForm.controls['publicationYear'].value, <string>this.genusForm.controls['ancestorId'].value).subscribe(() => {
        this.genusForm.reset();
        this.toast.success('Genus successfully created');
        this.loadTaxons.emit();
      }, error => {
        this.toast.warning('Something went wrong');
        console.error('Error:', error);
      });
    } else if(this.genusForm.valid && this.action === 'Update') {
      this.editService.updateTaxon('genus', <string>this.genusForm.get('id')!.value, <string>this.genusForm.get('scientificName')!.value, <string>this.genusForm.controls['author'].value, <string>this.genusForm.controls['publicationYear'].value, <string>this.genusForm.controls['ancestorId'].value).subscribe(() => {
        this.genusForm.reset();
        this.resetForm();
        this.toast.success('Genus successfully updated');
        this.loadTaxons.emit();
      }, error => {
        this.toast.warning('Something went wrong');
      });
    }
  }

  public deleteTaxon(scientificName: string) {
    this.editService.deleteTaxon('genus', scientificName).subscribe(() => {
      this.toast.success('Genus successfully deleted');
      this.loadTaxons.emit();
    }, error => {
      this.toast.warning('Something went wrong');
      this.loadTaxons.emit();
    });
  }

  public resetForm() {
    this.genusForm.reset();
    this.selectedItem = undefined;

    if(this.action === 'Update') {
      this.genusForm.controls['id'].disable();
      this.genusForm.controls['scientificName'].disable();
      this.genusForm.controls['author'].disable();
      this.genusForm.controls['publicationYear'].disable();
      this.isDisabled = true;
    } else {
      this.genusForm.controls['id'].enable();
      this.genusForm.controls['scientificName'].enable();
      this.genusForm.controls['author'].enable();
      this.genusForm.controls['publicationYear'].enable();
    }
  }

  public selectTaxon(id: string, scientificName: string, author: string, publicationYear: string, ancestorId: string) {
    this.selectedItem = scientificName;

    this.genusForm.controls['id'].enable();
    this.genusForm.controls['scientificName'].enable();
    this.genusForm.controls['author'].enable();
    this.genusForm.controls['publicationYear'].enable();
    this.isDisabled = false;

    this.genusForm.controls['id'].setValue(id);
    this.genusForm.controls['scientificName'].setValue(scientificName);
    this.genusForm.controls['author'].setValue(author);
    this.genusForm.controls['publicationYear'].setValue(publicationYear);
    this.genusForm.controls['ancestorId'].setValue(ancestorId);
    this.genusForm.controls['ancestorIdName'].setValue(this.families.find(family => family.id === ancestorId).scientificName);
  }

  public get totalPages(): number {
    return Math.ceil(this.genuses.length / this.itemsPerPage);
  }

  public get paginatedItems(): any[] {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    const endIndex = startIndex + this.itemsPerPage;
    return this.genuses.slice(startIndex, endIndex);
  }

  public changePage(newPage: number): void {
    if (newPage >= 1 && newPage <= this.totalPages) {
      this.currentPage = newPage;
    }
  }

  static validateName(control: AbstractControl) {
    if(control.value && (control.value.charAt(0).toUpperCase() !== control.value.charAt(0))) {
      return {invalid: true};
    } return null;
  }

  selectAncestor(id: string, scientificName: string) {
    if(id && scientificName) {
      this.genusForm.get('ancestorId')?.setValue(id);
      this.genusForm.get('ancestorIdName')?.setValue(scientificName);
    }
  }
}
