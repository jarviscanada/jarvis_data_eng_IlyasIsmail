import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditTraderDialogComponent } from './edit-trader-dialog.component';

describe('EditTraderDialogComponent', () => {
  let component: EditTraderDialogComponent;
  let fixture: ComponentFixture<EditTraderDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditTraderDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditTraderDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
