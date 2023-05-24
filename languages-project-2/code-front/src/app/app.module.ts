import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LayoutComponent } from './layout/layout.component';
import { NavbarComponent } from './includes/navbar/navbar.component';
import { EditComponent } from './layout/edit/edit.component';
import { LandingPageComponent } from './layout/landing-page/landing-page.component';
import { FooterComponent } from './includes/footer/footer.component';
import { SidebarComponent } from './layout/edit/sidebar/sidebar.component';
import { FormComponent } from './layout/edit/form/form.component';
import { DefaultComponent } from './layout/edit/default/default.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule} from "@angular/forms";
import { KingdomFormComponent } from './layout/edit/form/kingdom-form/kingdom-form.component';
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { ToastrModule } from "ngx-toastr";
import { DivisionFormComponent } from './layout/edit/form/division-form/division-form.component';
import { ClassFormComponent } from './layout/edit/form/class-form/class-form.component';
import { OrderFormComponent } from './layout/edit/form/order-form/order-form.component';
import { FamilyFormComponent } from './layout/edit/form/family-form/family-form.component';
import { ImagesComponent } from "./layout/edit/images/image.component";
import { GenusFormComponent } from './layout/edit/form/genus-form/genus-form.component';
import { SpeciesFormComponent } from './layout/edit/form/species-form/species-form.component';
import { NgxDropzoneModule } from "ngx-dropzone";
import { NgSelectModule } from "@ng-select/ng-select";
import {NgOptimizedImage} from "@angular/common";
import { ImageLibraryComponent } from './layout/image-library/image-library.component';
import { SingleImageComponent } from './layout/image-library/single-image/single-image.component';

@NgModule({
  declarations: [
    AppComponent,
    LayoutComponent,
    NavbarComponent,
    EditComponent,
    LandingPageComponent,
    FooterComponent,
    SidebarComponent,
    FormComponent,
    DefaultComponent,
    KingdomFormComponent,
    DivisionFormComponent,
    ClassFormComponent,
    OrderFormComponent,
    FamilyFormComponent,
    ImagesComponent,
    GenusFormComponent,
    SpeciesFormComponent,
    ImageLibraryComponent,
    SingleImageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    BrowserAnimationsModule, // required animations module
    ToastrModule.forRoot(), // ToastrModule added
    FormsModule,
    NgxDropzoneModule,
    NgSelectModule,
    NgOptimizedImage
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
