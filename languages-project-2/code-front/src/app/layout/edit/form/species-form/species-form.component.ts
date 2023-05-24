import {Component, EventEmitter, Input, Output, SimpleChanges} from '@angular/core';
import {AbstractControl, FormBuilder, Validators} from "@angular/forms";
import {EditService} from "../../../../services/edit.service";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-species-form',
  templateUrl: './species-form.component.html',
  styleUrls: ['./species-form.component.css']
})
export class SpeciesFormComponent {
  @Input() action: string = 'Create';
  @Input() species: Array<any> = [];
  @Input() genuses: Array<any> = [];
  @Output() loadTaxons = new EventEmitter();

  public itemsPerPage = 5;
  public currentPage = 1;
  public selectedItem: string | undefined;
  public isDisabled: boolean = true;

  public speciesForm = this.formBuilder.group({
    id: [''],
    scientificName: ['', [Validators.required, SpeciesFormComponent.validateName]],
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
    if(this.speciesForm.valid && this.action === 'Create') {
      this.editService.createTaxon('species', <string>this.speciesForm.get('scientificName')!.value, <string>this.speciesForm.controls['author'].value, <string>this.speciesForm.controls['publicationYear'].value, <string>this.speciesForm.controls['ancestorId'].value).subscribe(() => {
        this.speciesForm.reset();
        this.toast.success('Specie successfully created');
        this.loadTaxons.emit();
      }, error => {
        this.toast.warning('Something went wrong');
        console.error('Error:', error);
      });
    } else if(this.speciesForm.valid && this.action === 'Update') {
      this.editService.updateTaxon('species', <string>this.speciesForm.get('id')!.value, <string>this.speciesForm.get('scientificName')!.value, <string>this.speciesForm.controls['author'].value, <string>this.speciesForm.controls['publicationYear'].value, <string>this.speciesForm.controls['ancestorId'].value).subscribe(() => {
        this.speciesForm.reset();
        this.resetForm();
        this.toast.success('Specie successfully updated');
        this.loadTaxons.emit();
      }, error => {
        this.toast.warning('Something went wrong');
      });
    }
  }

  public deleteTaxon(scientificName: string) {
    this.editService.deleteTaxon('species', scientificName).subscribe(() => {
      this.toast.success('Specie successfully deleted');
      this.loadTaxons.emit();
    }, error => {
      this.toast.warning('Something went wrong');
      this.loadTaxons.emit();
    });
  }

  public resetForm() {
    this.speciesForm.reset();
    this.selectedItem = undefined;

    if(this.action === 'Update') {
      this.speciesForm.controls['id'].disable();
      this.speciesForm.controls['scientificName'].disable();
      this.speciesForm.controls['author'].disable();
      this.speciesForm.controls['publicationYear'].disable();
      this.isDisabled = true;
    } else {
      this.speciesForm.controls['id'].enable();
      this.speciesForm.controls['scientificName'].enable();
      this.speciesForm.controls['author'].enable();
      this.speciesForm.controls['publicationYear'].enable();
    }
  }

  public selectTaxon(id: string, scientificName: string, author: string, publicationYear: string, ancestorId: string) {
    this.selectedItem = scientificName;

    this.speciesForm.controls['id'].enable();
    this.speciesForm.controls['scientificName'].enable();
    this.speciesForm.controls['author'].enable();
    this.speciesForm.controls['publicationYear'].enable();
    this.isDisabled = false;

    this.speciesForm.controls['id'].setValue(id);
    this.speciesForm.controls['scientificName'].setValue(scientificName);
    this.speciesForm.controls['author'].setValue(author);
    this.speciesForm.controls['publicationYear'].setValue(publicationYear);
    this.speciesForm.controls['ancestorId'].setValue(ancestorId);
    this.speciesForm.controls['ancestorIdName'].setValue(this.genuses.find(genus => genus.id === ancestorId).scientificName);
  }

  public get totalPages(): number {
    return Math.ceil(this.species.length / this.itemsPerPage);
  }

  public get paginatedItems(): any[] {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    const endIndex = startIndex + this.itemsPerPage;
    return this.species.slice(startIndex, endIndex);
  }

  public changePage(newPage: number): void {
    if (newPage >= 1 && newPage <= this.totalPages) {
      this.currentPage = newPage;
    }
  }

  static validateName(control: AbstractControl) {
    if(control.value && (control.value.charAt(0).toLowerCase() !== control.value.charAt(0))) {
      return {invalid: true};
    } return null;
  }

  selectAncestor(id: string, scientificName: string) {
    if(id && scientificName) {
      this.speciesForm.get('ancestorId')?.setValue(id);
      this.speciesForm.get('ancestorIdName')?.setValue(scientificName);
    }
  }
}
