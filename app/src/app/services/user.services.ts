import {Injectable} from '@angular/core';
import {map, Observable} from 'rxjs';
import {UserModel} from '../core/model/UserModel';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {ENDPOINTS} from '../core/constans/endpoints';
import {AuthService} from './auth.service';
import {MessageResponse, UserDataResponse} from '../core/model/ApiResponseModel';

@Injectable({ providedIn: 'root' })
export class UserService {

  constructor(private http: HttpClient, private authService: AuthService) {}

    public getUsers(): Observable<UserModel[]> {
      const httpOptions = {
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${this.authService.getToken()}`
        }),
      }
      return this.http
        .get<UserDataResponse>(ENDPOINTS.USERS, httpOptions)
        .pipe(map(response => response.data));
    }

    public deleteUser(id: string): Observable<MessageResponse> {
      const httpOptions = {
        headers: new HttpHeaders({'Content-Type': 'application/json', 'Authorization': `Bearer ${this.authService.getToken()}`}),
      }
      return this.http.post<MessageResponse>(
        ENDPOINTS.DELETE_USER(id),
        null,
        httpOptions
      );
    }

    public createUser(email: string, password: string): Observable<MessageResponse> {
      const httpOptions = {
        headers: new HttpHeaders({'Content-Type': 'application/json', 'Authorization': `Bearer ${this.authService.getToken()}`}),
      }
      return this.http.post<MessageResponse>(
        ENDPOINTS.CREATE_USERS,
        {email, password},
        httpOptions
      );
    }
}
