import {Component} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {ImageService} from "../../services/image.service";
import {ToastrService} from "ngx-toastr";

interface Taxon {
  id: number;
  scientificName: string;
  author: string;
  publication_year: number;
  taxon_ancestor_id: number;
  image: string | null;
  taxonType: string;
  collecting_method: string | null;
}

@Component({
  selector: 'app-image-library',
  templateUrl: './image-library.component.html',
  styleUrls: ['./image-library.component.css']
})

export class ImageLibraryComponent {
  public filter: string = '';
  public images: Array<any> = [];
  public owners: Array<any> = [];
  public imagesFilter: Array<any> = []
  public itemsPerPage = 10;
  public currentPage = 1;

  constructor(private route: ActivatedRoute, private imageService: ImageService, private toast: ToastrService, private router: Router) {
    this.getImagesData();
  }

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.filter = params['search'];
    });
  }

  /**
   * Set the images data from the backend into a variable
   */
  public getImagesData() {
    this.imageService.getImageData().subscribe(data => {
      this.images = data['images'];
      this.imagesFilter = data['images'];
      this.owners = data['owner'];

      this.filterImages();
      console.log(this.owners)

      this.toast.success('Images succesfully loaded');
    }, error => {
      this.toast.warning('Something went wrong while trying to retrieve the images');
    })
  }

  /**
   * Filters the images on the current search
   */
  public filterImages() {
    if(!(this.filter)) {
      this.imagesFilter = this.images;
      return;
    }
    this.imagesFilter = this.images.filter(image => {
      const owner = this.owners.find(o => o.id === image.owner);
      const author = this.owners.find(o => o.id === image.author);
      return (
        Object.values(image).some(value => {
          return String(value)
            .toLowerCase()
            .includes(this.filter.trim().toLowerCase());
        }) ||
        image.taxons.some((taxon: Taxon) => {
          return Object.values(taxon).some(value => {
            return String(value)
              .toLowerCase()
              .includes(this.filter.trim().toLowerCase());
          })
        }) ||
        (owner && owner.name.toLowerCase().includes(this.filter.trim().toLowerCase()))
        ||
        (author && author.name.toLowerCase().includes(this.filter.trim().toLowerCase()))
      );
    });
  }

  /**
   * Paginator methods
   */
  public get totalPages(): number {
    return Math.ceil(this.imagesFilter.length / this.itemsPerPage);
  }

  public get paginatedItems(): any[] {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    const endIndex = startIndex + this.itemsPerPage;
    return this.imagesFilter.slice(startIndex, endIndex);
  }

  public changePage(newPage: number): void {
    if (newPage >= 1 && newPage <= this.totalPages) {
      this.currentPage = newPage;
    }
  }

  /**
   * Loads a single image when the card is clicked
   *
   * @param id
   */
  public loadSingleImage(id: number) {
    this.router.navigate(['images/' + id] );
  }
}
