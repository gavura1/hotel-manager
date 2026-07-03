import { TestBed } from '@angular/core/testing';
import { AuthService } from './auth.service';
import { Router } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';

function createFakeJwt(payload: object): string {
  const header = btoa(JSON.stringify({ alg: 'HS256', typ: 'JWT' }));
  const body = btoa(JSON.stringify(payload));
  return `${header}.${body}.fake-signature`;
}

function mockLocalStorage() {
  let store: Record<string, string> = {};
  Object.defineProperty(window, 'localStorage', {
    value: {
      getItem: (key: string) => (key in store ? store[key] : null),
      setItem: (key: string, value: string) => {
        store[key] = value;
      },
      removeItem: (key: string) => {
        delete store[key];
      },
      clear: () => {
        store = {};
      },
    },
    writable: true,
    configurable: true,
  });
}

describe('AuthService', () => {
  let service: AuthService;

  beforeEach(() => {
    mockLocalStorage();
    TestBed.configureTestingModule({
      providers: [
        AuthService,
        provideHttpClient(),
        { provide: Router, useValue: { navigate: () => {} } },
      ],
    });
    service = TestBed.inject(AuthService);
  });

  it('should save and retrieve a token', () => {
    service.saveToken('abc123');
    expect(service.getToken()).toBe('abc123');
  });

  it('should not save an empty token', () => {
    service.saveToken('');
    expect(service.getToken()).toBeNull();
  });

  it('should clear token on logout', () => {
    service.saveToken('abc123');
    service.logout();
    expect(service.getToken()).toBeNull();
  });

  it('should correctly identify an admin user from JWT payload', () => {
    const token = createFakeJwt({ sub: 'admin@gmail.com', role: 'ADMIN' });
    service.saveToken(token);

    expect(service.isAdmin()).toBe(true);
    expect(service.isManager()).toBe(false);
    expect(service.getCurrentUserEmail()).toBe('admin@gmail.com');
  });

  it('should correctly identify a manager as adminOrManager', () => {
    const token = createFakeJwt({ sub: 'manager1@gmail.com', role: 'MANAGER' });
    service.saveToken(token);

    expect(service.isAdminOrManager()).toBe(true);
    expect(service.isAdmin()).toBe(false);
  });

  it('should return null role when no token exists', () => {
    expect(service.getCurrentUserRole()).toBeNull();
    expect(service.isAdmin()).toBe(false);
  });
});
