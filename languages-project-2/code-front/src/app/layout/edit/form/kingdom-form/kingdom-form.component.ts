import {Component, EventEmitter, Input, OnInit, Output, SimpleChanges} from '@angular/core';
import {AbstractControl, FormBuilder, Validators} from "@angular/forms";
import {EditService} from "../../../../services/edit.service";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-kingdom-form',
  templateUrl: './kingdom-form.component.html',
  styleUrls: ['./kingdom-form.component.css']
})
export class KingdomFormComponent implements OnInit{
  @Input() action: string = 'Create';
  @Input() kingdoms: Array<any> = [];
  @Output() loadTaxons = new EventEmitter();

  public itemsPerPage = 5;
  public currentPage = 1;
  public selectedItem: string | undefined;

  public kingdomForm = this.formBuilder.group({
    id: [''],
    scientificName: ['', [Validators.required, KingdomFormComponent.validateName]],
    author: ['', [Validators.required]],
    publicationYear: ['', [Validators.required]]
  });

  constructor(private formBuilder: FormBuilder, private editService: EditService, private toast: ToastrService) { }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['action'] && changes['action'].currentValue) {
      this.resetForm();
    }
  }

  ngOnInit() {}

  public submitForm() {
    if(this.kingdomForm.valid && this.action === 'Create') {
      this.editService.createTaxon('kingdom', <string>this.kingdomForm.get('scientificName')!.value, <string>this.kingdomForm.controls['author'].value, <string>this.kingdomForm.controls['publicationYear'].value).subscribe(() => {
        this.kingdomForm.reset();
        this.toast.success('Kingdom successfully created');
        this.loadTaxons.emit();
      }, error => {
        this.toast.warning('Something went wrong');
      });
    } else if(this.kingdomForm.valid && this.action === 'Update') {
      this.editService.updateTaxon('kingdom', <string>this.kingdomForm.get('id')!.value, <string>this.kingdomForm.get('scientificName')!.value, <string>this.kingdomForm.controls['author'].value, <string>this.kingdomForm.controls['publicationYear'].value).subscribe(() => {
        this.kingdomForm.reset();
        this.resetForm();
        this.toast.success('Kingdom successfully updated');
        this.loadTaxons.emit();
      }, error => {
        this.toast.warning('Something went wrong');
      });
    }
  }

  public deleteKingdom(scientificName: string) {
      this.editService.deleteTaxon('kingdom', scientificName).subscribe(() => {
        this.toast.success('Kingdom successfully deleted');
        this.loadTaxons.emit();
      }, error => {
        this.toast.warning('Something went wrong');
        this.loadTaxons.emit();
      });
  }

  public resetForm() {
    this.kingdomForm.reset();
    this.selectedItem = undefined;

    if(this.action === 'Update') {
      this.kingdomForm.controls['id'].disable();
      this.kingdomForm.controls['scientificName'].disable();
      this.kingdomForm.controls['author'].disable();
      this.kingdomForm.controls['publicationYear'].disable();
    } else {
      this.kingdomForm.controls['id'].enable();
      this.kingdomForm.controls['scientificName'].enable();
      this.kingdomForm.controls['author'].enable();
      this.kingdomForm.controls['publicationYear'].enable();
    }
  }

  public selectKingdom(id: string, scientificName: string, author: string, publicationYear: string) {
    this.selectedItem = scientificName;

    this.kingdomForm.controls['id'].enable();
    this.kingdomForm.controls['scientificName'].enable();
    this.kingdomForm.controls['author'].enable();
    this.kingdomForm.controls['publicationYear'].enable();

    this.kingdomForm.controls['id'].setValue(id);
    this.kingdomForm.controls['scientificName'].setValue(scientificName);
    this.kingdomForm.controls['author'].setValue(author);
    this.kingdomForm.controls['publicationYear'].setValue(publicationYear);
  }

  public get totalPages(): number {
    return Math.ceil(this.kingdoms.length / this.itemsPerPage);
  }

  public get paginatedItems(): any[] {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    const endIndex = startIndex + this.itemsPerPage;
    return this.kingdoms.slice(startIndex, endIndex);
  }

  public changePage(newPage: number): void {
    if (newPage >= 1 && newPage <= this.totalPages) {
      this.currentPage = newPage;
    }
  }

  static validateName(control: AbstractControl) {
    if(control.value && control.value.charAt(0).toUpperCase() !== control.value.charAt(0)) {
      return {invalid: true};
    } return null;
  }
}
