import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { TraderListService } from '../trader-list.service';
import { Trader } from '../trader';
import { formatDate } from '@angular/common';

@Component({
  selector: 'app-add-trader-dialog',
  templateUrl: './add-trader-dialog.component.html',
  styleUrls: ['./add-trader-dialog.component.css']
})
export class AddTraderDialogComponent implements OnInit {

  constructor(private service: TraderListService, private ref:MatDialogRef<AddTraderDialogComponent>, private builder:FormBuilder) { }

  ngOnInit(): void {
  }

  traderForm = this.builder.group({
    fName: this.builder.control(''),
    lName: this.builder.control(''),
    email: this.builder.control(''),
    country: this.builder.control(''),
    dob: this.builder.control('')
  })

  addTrader() {
    let trader: Trader = {
      key: 0,
      id: 0,
      firstName: this.traderForm.value.fName,
      lastName: this.traderForm.value.lName,
      dateOfBirth: formatDate(this.traderForm.value.dob, 'yyyy-MM-dd', 'en-US'),
      country: this.traderForm.value.country,
      email: this.traderForm.value.email,
      amount: 0
    }
    this.service.addTrader(trader);
    console.log(this.traderForm.value);
  }
}
