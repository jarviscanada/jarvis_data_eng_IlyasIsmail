import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { Trader } from '../trader';
import { TraderListService } from '../trader-list.service';
import { MatDialog } from '@angular/material/dialog';
import { AddFundsDialogComponent } from '../add-funds-dialog/add-funds-dialog.component';
import { RemoveFundsDialogComponent } from '../remove-funds-dialog/remove-funds-dialog.component';

@Component({
  selector: 'app-trader-account',
  templateUrl: './trader-account.component.html',
  styleUrls: ['./trader-account.component.css']
})

export class TraderAccountComponent implements OnInit {

  //tempId!: number;
  trader: Trader = {
    id: -1,
    key: 0,
    firstName: '',
    lastName: '',
    dateOfBirth: '',
    country: '',
    email: '',
    amount: 0
  };

  constructor(private activatedRoute: ActivatedRoute, private service: TraderListService, public dialog: MatDialog) { }

  ngOnInit(): void {
    this.activatedRoute.queryParams
    .subscribe(params => {
      this.trader.id = params['id'];
      this.trader.firstName = params["firstName"];
      this.trader.lastName = params['lastName'];
      this.trader.dateOfBirth = params['dateOfBirth'];
      this.trader.country = params['country'];
      this.trader.email = params['email'];
      this.trader.amount = params['amount'];
    })
    /** 
    this.tempId = this.activatedRoute.snapshot.params['id'];
    console.log(this.tempId);*/
  }

  addFunds(event: Event):void {
    let dialog = this.dialog.open(AddFundsDialogComponent, {
      width:'500px',
      height:'250px'
    });

    dialog.componentInstance.id = this.trader.id;

    dialog.afterClosed().subscribe(result => {
      console.log("Dialog result: " + result);
    });
  }

  removeFunds(event: Event):void {
    let dialog = this.dialog.open(RemoveFundsDialogComponent, {
      width:'500px',
      height:'250px'
    });

    dialog.componentInstance.id = this.trader.id;

    dialog.afterClosed().subscribe(result => {
      console.log("Dialog result: " + result);
    });
  }


}
