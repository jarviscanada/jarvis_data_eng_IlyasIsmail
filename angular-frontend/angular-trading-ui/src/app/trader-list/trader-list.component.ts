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
    this.service.getDataSource().subscribe(data => {
      this.traders = data;
      this.dataSource = new MatTableDataSource(this.traders);
    });
  }

  deleteTrader(event: Event, id: number): void {
    try {
      this.service.deleteTrader(id);
    } catch (error) {
      console.log(error);
    }
  }

  editTrader(event: Event, id: number): void {
    this.router.navigate(['/trader-account/' + id]);
  }

  addTrader(event: Event): void {
    let dialog = this.dialog.open(AddTraderDialogComponent, {
      width:'500px',
      height:'750px'
    });
  }

  displayColumns: string[] = this.service.getColumns();
  //dataSource = new MatTableDataSource<Trader>(this.traders);
}
