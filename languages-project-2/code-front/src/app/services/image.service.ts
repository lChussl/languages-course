import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment.development";

@Injectable({
  providedIn: 'root'
})
export class ImageService {

  constructor(private http: HttpClient) { }

  /**
   * Bring the images data from the backend
   *
   * @return data retrieved from the backend
   */
  public getImageData() {
    const endPoint = 'images';

    return this.http.get<any>(environment.apiUrl + endPoint);
  }

  /**
   * Retrieves single image information
   *
   * @param id
   *
   * @return singe image data retrieved from the backend
   */
  public getSingleImageData(id: number) {
    const endPoint = 'images/' + id;

    return this.http.get<any>(environment.apiUrl + endPoint);
  }
}
