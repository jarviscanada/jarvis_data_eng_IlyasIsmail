import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { TraderListService } from '../trader-list.service';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-edit-trader-dialog',
  styleUrls: ['./edit-trader-dialog.component.css'],
  template: `
    <form #f="ngForm" (ngSubmit)="onSubmit(f)" novalidate>
      <input name="first" ngModel  required #first="ngModel"/>
      <input name="last" ngModel />
      <input name="email" ngModel />
      <button (click)="onSubmit(f)" mat-button mat-dialog-close>Submit</button>
    </form>

    <p>First name value: {{ first.value }}</p>
    <p>First name valid: {{ first.valid }}</p>
    <p>Form value: {{ f.value | json }}</p>
    <p>Form valid: {{ f.valid }}</p>
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
