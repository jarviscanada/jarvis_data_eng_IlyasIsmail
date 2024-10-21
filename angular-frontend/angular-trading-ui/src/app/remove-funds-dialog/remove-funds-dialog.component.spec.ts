import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RemoveFundsDialogComponent } from './remove-funds-dialog.component';

describe('RemoveFundsDialogComponent', () => {
  let component: RemoveFundsDialogComponent;
  let fixture: ComponentFixture<RemoveFundsDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RemoveFundsDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RemoveFundsDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
