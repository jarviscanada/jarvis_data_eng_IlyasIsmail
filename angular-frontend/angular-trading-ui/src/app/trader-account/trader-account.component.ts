import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { Trader } from '../trader';
import { TraderListService } from '../trader-list.service';
import { MatDialog } from '@angular/material/dialog';
import { AddFundsDialogComponent } from '../add-funds-dialog/add-funds-dialog.component';
import { RemoveFundsDialogComponent } from '../remove-funds-dialog/remove-funds-dialog.component';
import { EditTraderDialogComponent } from '../edit-trader-dialog/edit-trader-dialog.component';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-trader-account',
  templateUrl: './trader-account.component.html',
  styleUrls: ['./trader-account.component.css']
})

export class TraderAccountComponent implements OnInit {

  testing$!: Observable<Trader[]>;
  trader!: Trader;

  constructor(private activatedRoute: ActivatedRoute, private service: TraderListService, public dialog: MatDialog) { }

  ngOnInit(): void {
    let id: number = this.activatedRoute.snapshot.params['id'];
    this.trader = this.service.getTrader(id);
    console.log(this.trader);
  }

  addFunds(event: Event):void {
    let dialog = this.dialog.open(AddFundsDialogComponent, {
      width:'500px',
      height:'250px'
    });

    dialog.componentInstance.id = this.trader.id;
  }

  removeFunds(event: Event):void {
    let dialog = this.dialog.open(RemoveFundsDialogComponent, {
      width:'500px',
      height:'250px'
    });

    dialog.componentInstance.id = this.trader.id;
  }

  editProfile(event: Event): void {
    let dialog = this.dialog.open(EditTraderDialogComponent, {
      width: '500px',
      height: '500px'
    });

    dialog.componentInstance.id = this.trader.id;
  }


}
