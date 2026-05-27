import { Routes } from '@angular/router';

import { DashboardLayout } from './layout/dashboard-layout/dashboard-layout';

import { HotelList } from './features/hotels/pages/hotel-list/hotel-list';


import { RoomList } from './features/rooms/pages/room-list/room-list';
import { RoomForm } from './features/rooms/pages/room-form/room-form';

export const routes: Routes = [
  {
    path: '',
    component: DashboardLayout,

    children: [
      {
        path: '',
        redirectTo: 'hotely',
        pathMatch: 'full',
      },

      // HOTELY

      {
        path: 'hotely',
        component: HotelList,
      },


      // IZBY

      {
        path: 'izby',
        component: RoomList,
      },

      {
        path: 'izby/vytvorit',
        component: RoomForm,
      },

      {
        path: 'izby/upravit/:id',
        component: RoomForm,
      },
    ],
  },
];
