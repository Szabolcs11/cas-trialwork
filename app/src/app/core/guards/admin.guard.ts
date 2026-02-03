import { inject } from '@angular/core';
import { Router , CanActivateFn } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { PATHS } from '../constans/paths';

export const adminGuard: CanActivateFn = async () => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.isAuthenticated()) {
    const user = await authService.getUser();
    if (user.admin) return true;
  }

  return router.parseUrl(PATHS.ENVIRONMENTS);
};
