import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {EditComponent} from "./layout/edit/edit.component";
import {LandingPageComponent} from "./layout/landing-page/landing-page.component";
import {FormComponent} from "./layout/edit/form/form.component";
import {DefaultComponent} from "./layout/edit/default/default.component";
import {ImagesComponent} from "./layout/edit/images/image.component";
import {ImageLibraryComponent} from "./layout/image-library/image-library.component";
import {SingleImageComponent} from "./layout/image-library/single-image/single-image.component";

const routes: Routes = [
  {
    path: '',
    component: LandingPageComponent
  },
  {
    path: 'edit',
    component: EditComponent,
    children: [
      {
        path: '',
        component: DefaultComponent
      },
      {
        path: 'taxon/:taxonType',
        component: FormComponent
      },
      {
        path: 'images',
        component: ImagesComponent
      }
    ]
  },
  {
    path: 'images',
    component: ImageLibraryComponent
  },
  {
    path: 'images/:id',
    component: SingleImageComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
