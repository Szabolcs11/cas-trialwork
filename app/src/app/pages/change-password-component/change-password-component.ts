import {Component} from '@angular/core';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {HttpClient} from '@angular/common/http';
import {AuthService} from '../../services/auth.service';

@Component({
  standalone: true,
  selector: 'app-change-password-component',
  imports: [
    FormsModule,
    ReactiveFormsModule
  ],
  templateUrl: './change-password-component.html',
  styleUrl: './change-password-component.scss',
})
export class ChangePasswordComponent {
  public changePasswordForm = new FormGroup({
    password: new FormControl('', Validators.required),
    passwordAgain: new FormControl('', Validators.required),
  });

  constructor(private http: HttpClient, private authService: AuthService) {}

  public onSubmit(): void {
    if (this.changePasswordForm.invalid) return;
    const value = this.changePasswordForm.value;
    if (value.password && value.passwordAgain) {
        this.authService.changePassword(value.password, value.passwordAgain);
    }
  }

}
