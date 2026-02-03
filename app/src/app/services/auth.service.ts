import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { ENDPOINTS } from '../core/constans/endpoints';
import {Router} from '@angular/router';
import {PATHS} from '../core/constans/paths';
import {MatSnackBar} from '@angular/material/snack-bar';
import {UserModel} from '../core/model/UserModel';
import {firstValueFrom} from 'rxjs';
import {AuthenticateResponse, LoginResponse, MessageResponse} from '../core/model/ApiResponseModel';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private router = inject(Router);

  private readonly TOKEN_KEY = 'jwt_token';
  private user: UserModel | null = null;

  constructor(private http: HttpClient, private snackBar: MatSnackBar) {
  }

  logIn(email: string, password: string) {
    const httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json'})
    }
    this.http.post<LoginResponse>(ENDPOINTS.LOGIN, {email, password}, httpOptions).subscribe((e) => {
      if (e.success) {
        localStorage.setItem(this.TOKEN_KEY, e.data.token);
        this.snackBar.open('Successfully logged in', 'Ok')

        if (!e.data.lastLogin) {
          this.router.navigate([PATHS.CHANGE_PASSWORD]);
        } else {
          this.router.navigate([PATHS.ENVIRONMENTS]);
        }
      } else {
        this.snackBar.open(e.message!, 'Ok')
      }
    })
  }

  changePassword(password: string, passwordAgain: string) {
    const httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'Authorization': `Bearer ${this.getToken()}`}),
    }
    this.http.post<MessageResponse>(ENDPOINTS.CHANGE_PASSWORD, {
      password: password,
      passwordAgain: passwordAgain
    }, httpOptions).subscribe((e) => {
      this.snackBar.open(e.message, "Ok");
      if (e.success) {
        this.router.navigate([PATHS.ENVIRONMENTS]);
      }
    })
  }

  authenticate() {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${this.getToken()}`
      })
    };

    return firstValueFrom(
      this.http.get<AuthenticateResponse>(ENDPOINTS.ME, httpOptions)
    ).then(res => {
      this.user = res.data;
      return this.user;
    });
  }

  logout(): void {
    localStorage.removeItem(this.TOKEN_KEY);
  }

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  isAuthenticated(): boolean {
    const token = this.getToken();
    if (!token || this.isTokenExpired(token)) return false;

    this.authenticate();

    return true;
  }

  async getUser(): Promise<UserModel> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${this.getToken()}`
      })
    };

    const res = await firstValueFrom(
      this.http.get<AuthenticateResponse>(ENDPOINTS.ME, httpOptions)
    );

    this.user = res.data;
    return this.user;
  }

  isTokenExpired(token: string): boolean {
    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      const expiry = payload.exp * 1000;
      return Date.now() > expiry;
    } catch {
      return true;
    }
  }
}
