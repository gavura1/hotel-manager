import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';

import { HotelForm } from './hotel-form';

describe('HotelForm', () => {
  let component: HotelForm;
  let fixture: ComponentFixture<HotelForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HotelForm],
      providers: [provideRouter([]), provideHttpClient()],
    }).compileComponents();

    fixture = TestBed.createComponent(HotelForm);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
