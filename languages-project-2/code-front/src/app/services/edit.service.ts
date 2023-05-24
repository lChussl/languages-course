import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { environment } from "../../environments/environment.development";

@Injectable({
  providedIn: 'root'
})
export class EditService {

  constructor( private http: HttpClient ) { }

  /**
   * Gets the taxon data
   *
   * @return retrieved data from the backend
   */
  public getData(): Observable<any> {
    const endPoint = 'edit';

    return this.http.get<any>(environment.apiUrl + endPoint);
  }

  /**
   * Sends data to create new taxon
   *
   * @param type
   * @param scientificName
   * @param author
   * @param publicationYear
   * @param ancestorId
   * @param collectingMethod
   *
   * @return OK response or BAD_REQUEST response
   */
  public createTaxon(type: string, scientificName: string, author: string, publicationYear: string, ancestorId?: string, collectingMethod?: string) {
    const endPoint = 'taxon/add';

    const request: Record<string, string> = {
      type: type,
      scientificName: scientificName,
      author: author,
      publication_year: publicationYear
    }

    if(ancestorId) {
      request['ancestorId'] = ancestorId;
    }

    if(collectingMethod) {
      request['collectingMethod'] = collectingMethod;
    }

    return this.http.post(environment.apiUrl + endPoint, request);
  }

  /**
   * Sends data to update a taxon
   *
   * @param type
   * @param id
   * @param scientificName
   * @param author
   * @param publicationYear
   * @param ancestorId
   * @param collectingMethod
   *
   * @return OK response or BAD_REQUEST response
   */
  public updateTaxon(type: string, id: string, scientificName: string, author: string, publicationYear: string, ancestorId?: string, collectingMethod?: string) {
    const endPoint = 'taxon/update';

    const request: Record<string, string> = {
      id: id,
      type: type,
      scientificName: scientificName,
      author: author,
      publication_year: publicationYear
    }

    if(ancestorId) {
      request['ancestorId'] = ancestorId;
    }

    if(collectingMethod) {
      request['collectingMethod'] = collectingMethod;
    }

    return this.http.post(environment.apiUrl + endPoint, request);
  }

  /**
   * Delete a taxon
   *
   * @param type
   * @param scientificName
   *
   * @return OK response or BAD_REQUEST response
   */
  public deleteTaxon(type: string, scientificName: string) {
    const endPoint = 'taxon/delete';

    const request = {
      type: type,
      scientificName: scientificName
    }

    return this.http.post(environment.apiUrl + endPoint, request);
  }

  /**
   * Get information from backend for the image form
   *
   * @return data retrieved from the backend
   */
  public getImageData() {
    const endPoint = 'edit/image';

    return this.http.get<any>(environment.apiUrl + endPoint);
  }

  /**
   * Adds new image
   *
   * @param description
   * @param url
   * @param date
   * @param authorId
   * @param ownerId
   * @param keywords
   * @param license
   * @param taxonIds
   *
   * @return OK response or BAD_REQUEST response
   */
  public addImage(description: string, url: string, date: string, authorId: string, ownerId: string, keywords: string, license: string, taxonIds: string) {
    const endPoint = 'image/add';

    const request: Record<string, any> = {
      description: description,
      url: url,
      date: date,
      authorId: authorId,
      ownerId: ownerId,
      keywords: keywords,
      license: license,
      taxonIds: taxonIds
    }

    return this.http.post(environment.apiUrl + endPoint, request);
  }

  /**
   * Update an image
   *
   * @param imageId
   * @param description
   * @param url
   * @param date
   * @param authorId
   * @param ownerId
   * @param keywords
   * @param license
   * @param taxonIds
   *
   * @return OK response or BAD_REQUEST response
   */
  public updateImage(imageId: string, description: string, url: string, date: string, authorId: string, ownerId: string, keywords: string, license: string, taxonIds: string): Observable<any> {
    const endPoint = 'image/update';

    const request: Record<string, any> = {
      id: imageId,
      description: description,
      url: url,
      date: date,
      authorId: authorId,
      ownerId: ownerId,
      keywords: keywords,
      license: license,
      taxonIds: taxonIds
    }

    return this.http.post(environment.apiUrl + endPoint, request);
  }

  /**
   * Deletes an image by id
   *
   * @param imageId
   *
   * @return OK response or BAD_REQUEST response
   */
  public deleteImage(imageId: number): Observable<any> {
    const endPoint = 'image/delete';

    const request: Record<string, any> = {
      id: imageId
    };

    return this.http.post(environment.apiUrl + endPoint, request);
  }

  /**
   * Uploads a file in the backend server
   *
   * @param file
   *
   * @return OK response or BAD_REQUEST response
   */
  public uploadFile(file: File) {
    const endPoint = 'edit/addImage';

    const formData = new FormData();
    formData.append('file', file);

    return this.http.post<any>(environment.apiUrl + endPoint, formData);
  }
}
