import { Component, OnInit } from '@angular/core';
import { QuotesService } from '../quotes.service';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-quotes-list',
  templateUrl: './quotes-list.component.html',
  styleUrls: ['./quotes-list.component.css']
})
export class QuotesListComponent implements OnInit {

  quotes: any
  dataSource: any;

  constructor(private service: QuotesService) { }

  ngOnInit(): void {
    this.service.getQuotes().subscribe(data => {
      this.quotes = data;
      this.dataSource = new MatTableDataSource(this.quotes);
      console.log(this.quotes);
    })
  }

  displayColumns: string[] = this.service.getColumns();

}
