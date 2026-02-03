import {Injectable} from '@angular/core';
import {map, Observable} from 'rxjs';
import {FeatureModel} from '../core/model/FeatureModel';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {ENDPOINTS} from '../core/constans/endpoints';
import {AuthService} from './auth.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {FeatureDataResponse, FeaturesDataResponse, MessageResponse} from '../core/model/ApiResponseModel';


@Injectable({ providedIn: 'root' })
export class FeatureService {

  constructor(private http: HttpClient, private authService: AuthService, private snackBar: MatSnackBar) {
  }

  public getFeaturesByEnvironment(environment: string): Observable<FeatureModel[]> {
    const httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'Authorization': `Bearer ${this.authService.getToken()}`}),
    }
    return this.http.get<FeaturesDataResponse>(ENDPOINTS.FEATURES_ENVIRONMENT(environment), httpOptions)
      .pipe(map(response => response.data));
  }

  public toggleFeature(environmentId: string, featureId: string, state: boolean): void {
    const httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'Authorization': `Bearer ${this.authService.getToken()}`}),
    }
    this.http.post<MessageResponse>(ENDPOINTS.FEATURE_UPDATE(featureId), {environmentId, newState: state}, httpOptions).subscribe((e) => {
      if (e.success) {
        this.snackBar.open('Successfully updated feature state', 'Ok')
      } else {
        this.snackBar.open(e.message!, 'Ok')
      }
    })
  }

  public createFeature(identifier: string, name: string, description: string): Observable<MessageResponse> {
    const httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'Authorization': `Bearer ${this.authService.getToken()}`}),
    }
    return this.http.post<MessageResponse>(ENDPOINTS.CREATE_FEATURE, {identifier, name, description}, httpOptions);
  }

  public getFeature(featureId: string): Observable<FeatureDataResponse> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${this.authService.getToken()}`
      }),
    }
    return this.http.get<FeatureDataResponse>(ENDPOINTS.FEATURE_BY_ID(featureId), httpOptions).pipe(map(response => response));
  }

  public deleteFeature(featureId: string): Observable<MessageResponse> {
    const httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'Authorization': `Bearer ${this.authService.getToken()}`}),
    }
    return this.http.post<MessageResponse>(ENDPOINTS.DELETE_FEATURE(featureId), null, httpOptions);
  }
}
