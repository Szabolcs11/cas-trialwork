import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login-component/login-component';
import { ChangePasswordComponent } from './pages/change-password-component/change-password-component';
import { EnvironmentComponent } from './pages/environment-component/environment-component';
import { PATHS } from './core/constans/paths';
import { authGuard } from './core/guards/auth.guard';
import { guestGuard } from './core/guards/guest.guard';
import { FeaturesComponent } from './pages/features-component/features-component';
import { UsersComponent } from './pages/users-component/users-component';
import { FeatureComponent } from './pages/feature-component/feature-component';
import { adminGuard } from './core/guards/admin.guard';

export const routes: Routes = [
  { path: PATHS.LOGIN, component: LoginComponent, canActivate: [guestGuard] },
  { path: PATHS.CHANGE_PASSWORD, component: ChangePasswordComponent },
  { path: PATHS.ENVIRONMENTS, component: EnvironmentComponent, canActivate: [authGuard] },
  { path: PATHS.FEATURES, component: FeaturesComponent, canActivate: [authGuard]},
  { path: PATHS.FEATURES + "/:id", component: FeatureComponent, canActivate: [authGuard]},
  { path: PATHS.USERS, component: UsersComponent, canActivate: [adminGuard] }
];
