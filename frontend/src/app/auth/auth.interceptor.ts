import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from './auth.service';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);

  const token: string | null = authService.getToken();

  // Debug (dočasne – môžeš potom zmazať)
  console.log('[Interceptor] URL:', req.url);
  console.log('[Interceptor] Token exists:', !!token);

  // Ak nemáme token, posielame request bez úpravy
  if (!token) {
    return next(req);
  }

  // Klonovanie requestu s JWT headerom
  const authReq = req.clone({
    setHeaders: {
      Authorization: `Bearer ${token}`,
    },
  });

  return next(authReq);
};
