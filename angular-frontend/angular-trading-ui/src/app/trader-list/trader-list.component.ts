import { Component, OnInit } from '@angular/core';
import { TraderListService } from '../trader-list.service';
import { Observable } from 'rxjs';
import { Trader } from '../trader';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatDialog } from '@angular/material/dialog';
import { AddTraderDialogComponent } from '../add-trader-dialog/add-trader-dialog.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-trader-list',
  templateUrl: './trader-list.component.html',
  styleUrls: ['./trader-list.component.css']
})
export class TraderListComponent implements OnInit{

  traders!: Trader[];
  dataSource: any;

  constructor(private service: TraderListService, public dialog: MatDialog, private router: Router) { }

  ngOnInit(): void {
    if(!this.traders) {
      this.service.getDataSource().subscribe(data => {
        this.traders = data;
        this.dataSource = new MatTableDataSource(this.traders);
        console.log(this.traders);
        console.log(this.service.getColumns())
      })
    }
  }

  deleteTrader(event: Event, id: number): void {
    try {
      console.log("clicked");
      this.service.deleteTrader(id);
    } catch (error) {
      console.log(error);
    }
  }

  editTrader(event: Event, trader: Trader): void {
    let testTrader: Trader = trader;
    this.router.navigate(['/trader-account/' + trader.id], {queryParams: {
      "id": trader.id,
      "firstName": trader.firstName,
      "lastName": trader.lastName,
      "dateOfBirth": trader.dateOfBirth,
      "country": trader.country,
      "email": trader.email,
      "amount": trader.amount
    }});
  }

  addTrader(event: Event): void {
    let dialog = this.dialog.open(AddTraderDialogComponent, {
      width:'500px',
      height:'750px'
    });

    dialog.afterClosed().subscribe(result => {
      console.log("Dialog result: " + result);
    });
  }

  displayColumns: string[] = this.service.getColumns();
  //dataSource = new MatTableDataSource<Trader>(this.traders);
}
