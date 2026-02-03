import {Component, OnInit} from '@angular/core';
import {AsyncPipe} from '@angular/common';
import {Observable} from 'rxjs';
import {UserModel} from '../../core/model/UserModel';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {MatSnackBar} from '@angular/material/snack-bar';
import {UserService} from '../../services/user.services';
import {NavigationComponent} from '../../components/navigation-component/navigation-component';
import {MatIconModule} from '@angular/material/icon';
import {createRandomString} from '../../util/characterGenerator';

@Component({
  standalone: true,
  selector: 'app-users-component',
  imports: [
    AsyncPipe,
    FormsModule,
    ReactiveFormsModule,
    NavigationComponent,
    MatIconModule
  ],
  templateUrl: './users-component.html',
  styleUrl: './users-component.scss',
})
export class UsersComponent implements OnInit{
  users: Observable<UserModel[]> | undefined;

  userForm = new FormGroup({
    email: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required)
  })

  constructor(private userService: UserService, private snackBar: MatSnackBar) {}

  async ngOnInit() {
    this.loadUsers();
  }

  loadUsers() {
    this.users = this.userService.getUsers().pipe((e) => e);
  }

  createUser() {
    if (this.userForm.invalid) return;
    const values = this.userForm.value as { email: string, password: string }
    this.userService.createUser(values.email, values.password).subscribe((e) => {
      this.loadUsers();
      this.snackBar.open(e.message, "Ok");
    });
  }

  deleteUser(id: string) {
    this.userService.deleteUser(id).subscribe((e) => {
      this.loadUsers();
      this.snackBar.open(e.message, "Ok");
    });
  }

  copyPassword(): void {
    const password = this.userForm.get('password')?.value;
    if (!password) return;

    navigator.clipboard.writeText(password);
  }

  generatePassword(): void {
    const password = createRandomString(12);
    this.userForm.get('password')?.setValue(password);
  }

}
