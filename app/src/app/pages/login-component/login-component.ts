import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
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
export class LoginComponent implements OnInit {

  constructor(private http: HttpClient, private auth: AuthService) {}

  ngOnInit() {}

  loginForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', Validators.required),
  });

  onSubmit() {
    if (this.loginForm.invalid) return;
    const value = this.loginForm.value as { email: string, password: string};
    this.auth.logIn(value.email, value.password)
  }
}
