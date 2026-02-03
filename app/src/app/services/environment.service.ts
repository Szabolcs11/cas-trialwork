import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {ENDPOINTS} from '../core/constans/endpoints';
import {EnvironmentModel} from '../core/model/EnvironmentModel';
import {AuthService} from './auth.service';
import {map, Observable} from 'rxjs';
import {EnvironmentModelResponse, MessageResponse} from '../core/model/ApiResponseModel';

@Injectable({ providedIn: 'root' })
export class EnvironmentService {

  constructor(private http: HttpClient, private authService: AuthService) {
  }

  public getEnvironments(): Observable<EnvironmentModel[]> {
    const httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'Authorization': `Bearer ${this.authService.getToken()}`}),
    }
    return this.http
      .get<EnvironmentModelResponse>(ENDPOINTS.ENVIRONMENTS, httpOptions)
      .pipe(map(response => response.data));
  }

  public createEnvironment(name: string): Observable<MessageResponse> {
    const httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'Authorization': `Bearer ${this.authService.getToken()}`}),
    }
    return this.http.post<MessageResponse>(
      ENDPOINTS.ADD_ENVIRONMENT,
      { name },
      httpOptions
    );
  }

  public deleteEnvironment(id: string): Observable<MessageResponse> {
    const httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'Authorization': `Bearer ${this.authService.getToken()}`}),
    }
    return this.http.post<MessageResponse>(
      ENDPOINTS.DELETE_ENVIRONMENT(id),
      null,
      httpOptions
    );
  }
}
