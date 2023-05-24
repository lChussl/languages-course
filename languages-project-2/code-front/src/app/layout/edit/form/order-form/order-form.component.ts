import {Component, EventEmitter, Input, Output, SimpleChanges} from '@angular/core';
import {AbstractControl, FormBuilder, Validators} from "@angular/forms";
import {EditService} from "../../../../services/edit.service";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-order-form',
  templateUrl: './order-form.component.html',
  styleUrls: ['./order-form.component.css']
})

export class OrderFormComponent {
  @Input() action: string = 'Create';
  @Input() orders: Array<any> = [];
  @Input() classes: Array<any> = [];
  @Output() loadTaxons = new EventEmitter();

  public itemsPerPage = 5;
  public currentPage = 1;
  public selectedItem: string | undefined;
  public isDisabled: boolean = true;

  public orderForm = this.formBuilder.group({
    id: [''],
    scientificName: ['', [Validators.required, OrderFormComponent.validateName]],
    author: ['', [Validators.required]],
    publicationYear: ['', [Validators.required]],
    ancestorId: ['', [Validators.required]],
    ancestorIdName: [''],
    collectingMethod: ['', [Validators.required]]
  });

  constructor(private formBuilder: FormBuilder, private editService: EditService, private toast: ToastrService) { }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['action'] && changes['action'].currentValue) {
      this.resetForm();
    }
  }

  ngOnInit() {}

  public submitForm() {
    if(this.orderForm.valid && this.action === 'Create') {
      this.editService.createTaxon('order', <string>this.orderForm.get('scientificName')!.value, <string>this.orderForm.controls['author'].value, <string>this.orderForm.controls['publicationYear'].value, <string>this.orderForm.controls['ancestorId'].value, <string>this.orderForm.controls['collectingMethod'].value).subscribe(() => {
        this.orderForm.reset();
        this.toast.success('Order successfully created');
        this.loadTaxons.emit();
      }, error => {
        this.toast.warning('Something went wrong');
      });
    } else if(this.orderForm.valid && this.action === 'Update') {
      this.editService.updateTaxon('order', <string>this.orderForm.get('id')!.value, <string>this.orderForm.get('scientificName')!.value, <string>this.orderForm.controls['author'].value, <string>this.orderForm.controls['publicationYear'].value, <string>this.orderForm.controls['ancestorId'].value, <string>this.orderForm.controls['collectingMethod'].value).subscribe(() => {
        this.orderForm.reset();
        this.resetForm();
        this.toast.success('Order successfully updated');
        this.selectedItem = undefined;
        this.loadTaxons.emit();
      }, error => {
        this.toast.warning('Something went wrong');
      });
    }
  }

  public deleteTaxon(scientificName: string) {
    this.editService.deleteTaxon('order', scientificName).subscribe(() => {
      this.toast.success('Order successfully deleted');
      this.loadTaxons.emit();
    }, error => {
      this.toast.warning('Something went wrong');
      this.loadTaxons.emit();
    });
  }

  public resetForm() {
    this.orderForm.reset();
    this.selectedItem = undefined;

    if(this.action === 'Update') {
      this.orderForm.controls['id'].disable();
      this.orderForm.controls['scientificName'].disable();
      this.orderForm.controls['author'].disable();
      this.orderForm.controls['publicationYear'].disable();
      this.isDisabled = true;
    } else {
      this.orderForm.controls['id'].enable();
      this.orderForm.controls['scientificName'].enable();
      this.orderForm.controls['author'].enable();
      this.orderForm.controls['publicationYear'].enable();
    }
  }

  public selectTaxon(id: string, scientificName: string, author: string, publicationYear: string, ancestorId: string, collectingMethod: string) {
    this.selectedItem = scientificName;

    this.orderForm.controls['id'].enable();
    this.orderForm.controls['scientificName'].enable();
    this.orderForm.controls['author'].enable();
    this.orderForm.controls['publicationYear'].enable();
    this.isDisabled = false;

    this.orderForm.controls['id'].setValue(id);
    this.orderForm.controls['scientificName'].setValue(scientificName);
    this.orderForm.controls['author'].setValue(author);
    this.orderForm.controls['publicationYear'].setValue(publicationYear);
    this.orderForm.controls['ancestorId'].setValue(ancestorId);
    this.orderForm.controls['ancestorIdName'].setValue(this.classes.find(clas => clas.id ===  ancestorId).scientificName);
    this.orderForm.controls['collectingMethod'].setValue(collectingMethod);
  }

  public selectCollectingMethod(collectingMethod: string) {
    this.orderForm.controls['collectingMethod'].setValue(collectingMethod);
  }

  public get totalPages(): number {
    return Math.ceil(this.orders.length / this.itemsPerPage);
  }

  public get paginatedItems(): any[] {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    const endIndex = startIndex + this.itemsPerPage;
    return this.orders.slice(startIndex, endIndex);
  }

  public changePage(newPage: number): void {
    if (newPage >= 1 && newPage <= this.totalPages) {
      this.currentPage = newPage;
    }
  }

  static validateName(control: AbstractControl) {
    if(control.value && (control.value.charAt(0).toUpperCase() !== control.value.charAt(0) || !(control.value.endsWith('ales')))) {
      return {invalid: true};
    } return null;
  }

  selectAncestor(id: string, scientificName: string) {
    if(id && scientificName) {
      this.orderForm.get('ancestorId')?.setValue(id);
      this.orderForm.get('ancestorIdName')?.setValue(scientificName);
    }
  }
}
