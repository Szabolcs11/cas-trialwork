import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import {HttpClient} from '@angular/common/http';
import {AuthService} from '../../services/auth.service';
import {MatSnackBar} from '@angular/material/snack-bar';


@Component({
  standalone: true,
  selector: 'app-login-component',
  imports: [CommonModule, ReactiveFormsModule],
  providers: [MatSnackBar],
  templateUrl: './login-component.html',
  styleUrl: './login-component.scss',
})
export class LoginComponent {

  constructor(private http: HttpClient, private auth: AuthService) {}

  public loginForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', Validators.required),
  });

  public onSubmit(): void {
    if (this.loginForm.invalid) return;
    const value = this.loginForm.value as { email: string, password: string};
    this.auth.logIn(value.email, value.password)
  }
}
