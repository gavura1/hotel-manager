import { Routes } from '@angular/router';

import { DashboardLayout } from './layout/dashboard-layout/dashboard-layout';
import { HotelList } from './features/hotels/pages/hotel-list/hotel-list';
import { HotelForm } from './features/hotels/pages/hotel-form/hotel-form';
import { RoomList } from './features/rooms/pages/room-list/room-list';
import { RoomForm } from './features/rooms/pages/room-form/room-form';
import { AuthCallbackComponent } from './auth/auth-callback.component';
import { LoginComponent } from './auth/login.component';
import {authGuard} from './auth/auth.guard';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'auth/callback', component: AuthCallbackComponent },
  {
    path: '',
    component: DashboardLayout,
    canActivate: [authGuard],
    children: [
      {
        path: '',
        redirectTo: 'hotely',
        pathMatch: 'full',
      },


      // HOTELY
      { path: 'hotely', component: HotelList },
      { path: 'hotely/vytvorit', component: HotelForm },
      { path: 'hotely/upravit/:id', component: HotelForm },

      // IZBY
      { path: 'izby', component: RoomList },
      { path: 'izby/:hotelId', component: RoomList },
      { path: 'izby/:hotelId/vytvorit', component: RoomForm },
      { path: 'izby/:hotelId/upravit/:id', component: RoomForm },
    ],
  },
];
