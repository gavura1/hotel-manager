import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';

import { RoomForm } from './room-form';

describe('RoomForm', () => {
  let component: RoomForm;
  let fixture: ComponentFixture<RoomForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RoomForm],
      providers: [provideRouter([]), provideHttpClient()],
    }).compileComponents();

    fixture = TestBed.createComponent(RoomForm);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
