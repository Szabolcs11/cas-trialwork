import {Component, inject, OnInit} from '@angular/core';
import {Router, RouterLink, RouterLinkActive} from '@angular/router';
import {MatSnackBar} from '@angular/material/snack-bar';
import {AuthService} from '../../services/auth.service';
import {PATHS} from '../../core/constans/paths';

@Component({
  standalone: true,
  selector: 'app-navigation-component',
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './navigation-component.html',
  styleUrl: './navigation-component.scss',
})
export class NavigationComponent implements OnInit {
  private router = inject(Router);
  public isMenuOpen = false;
  public isAdmin = false;

  constructor(private authService: AuthService, private snackBar: MatSnackBar) {
  }

  public async ngOnInit(): Promise<void> {
    const user = await this.authService.getUser();
    if (user) this.isAdmin = user.admin;
  }

  public toggleMenu(): void {
    this.isMenuOpen = !this.isMenuOpen;
  }

  public logout(): void {
    this.authService.logout();
    this.snackBar.open("Successfully logged out", "Ok");
    this.router.navigate([PATHS.LOGIN]);
  }
}
