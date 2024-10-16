import { Component, OnInit } from '@angular/core';
import { TraderListService } from '../trader-list.service';
import { FormBuilder } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-add-funds-dialog',
  templateUrl: './add-funds-dialog.component.html',
  styleUrls: ['./add-funds-dialog.component.css']
})
export class AddFundsDialogComponent implements OnInit {

  id!: number;

  constructor(private service: TraderListService, private builder: FormBuilder) { }

  ngOnInit(): void {
  }

  depositForm = this.builder.group({
    amount: this.builder.control('')
  })

  addFunds():void {
    this.service.addFunds(this.id, this.depositForm.value.amount)
  }

}
