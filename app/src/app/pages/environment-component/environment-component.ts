import {Component, OnInit} from '@angular/core';
import {EnvironmentService} from '../../services/environment.service';
import {CommonModule} from '@angular/common';
import {EnvironmentModel} from '../../core/model/EnvironmentModel';
import {Observable} from 'rxjs';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {AuthService} from '../../services/auth.service';
import {UserModel} from '../../core/model/UserModel';
import {MatSnackBar} from '@angular/material/snack-bar';
import {NavigationComponent} from '../../components/navigation-component/navigation-component';

@Component({
  standalone: true,
  selector: 'app-environment-component',
  imports: [CommonModule, ReactiveFormsModule, NavigationComponent],
  templateUrl: './environment-component.html',
  styleUrl: './environment-component.scss',
})

export class EnvironmentComponent implements OnInit{
  environments: Observable<EnvironmentModel[]> | undefined;
  user: UserModel | undefined;
  isAdmin = false;

  environmentForm = new FormGroup({
    name: new FormControl('', Validators.required)
  })

  constructor(private environmentService: EnvironmentService, private authService: AuthService, private snackBar: MatSnackBar) {}

  async ngOnInit() {
    this.loadEnvironments();

    this.user = await this.authService.authenticate();
    this.isAdmin = this.user.admin;
  }

  loadEnvironments() {
    this.environments = this.environmentService.getEnvironments().pipe((e) => e)
  }

  createEnvironment() {
    if (this.environmentForm.invalid) return;
    this.environmentService.createEnvironment(this.environmentForm.value.name as string).subscribe((e) => {
      this.loadEnvironments();
      this.environmentForm.reset();
      this.snackBar.open(e.message, "Ok")
    })
  }

  deleteEnvironment(id: string) {
    this.environmentService.deleteEnvironment(id).subscribe((e) => {
      this.loadEnvironments();
      this.snackBar.open(e.message, "Ok")
    })
  }
}
