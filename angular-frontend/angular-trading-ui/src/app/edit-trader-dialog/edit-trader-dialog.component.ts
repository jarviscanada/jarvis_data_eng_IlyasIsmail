import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { TraderListService } from '../trader-list.service';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-edit-trader-dialog',
  styleUrls: ['./edit-trader-dialog.component.css'],
  template: `
    <form #f="ngForm" (ngSubmit)="onSubmit(f)" novalidate>
<h2 mat-dialog-title>Add New Trader</h2>
<mat-dialog-content>
    <p><mat-form-field>
        <mat-label>First Name</mat-label>
        <input name="first" matInput ngModel  required #first="ngModel"/>
    </mat-form-field></p>
    <p><mat-form-field>
        <mat-label>Last Name</mat-label>
        <input name="last" matInput ngModel />
    </mat-form-field></p>
    <p><mat-form-field>
        <mat-label>Email</mat-label>
        <input name="email" matInput ngModel />
    </mat-form-field></p>
</mat-dialog-content>
<mat-dialog-actions>
    <button (click)="onSubmit(f)" mat-button mat-dialog-close>Submit</button>
</mat-dialog-actions>
</form>
  `,
})
export class EditTraderDialogComponent implements OnInit {

  id!: number;

  constructor(private service: TraderListService, private ref: MatDialogRef<EditTraderDialogComponent>) { }

  ngOnInit(): void {
  }

  onSubmit(f: NgForm) {
    this.service.editTrader(this.id, f.value.first, f.value.last, f.value.email);
    console.log(f.value); // { first: '', last: '' }
    console.log(f.valid); // false
  }

}
