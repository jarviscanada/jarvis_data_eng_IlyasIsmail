import { Injectable } from '@angular/core';
import {Trader} from './trader';
import { BehaviorSubject, Observable, observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TraderListService {

  traderList: BehaviorSubject<Trader[]> = new BehaviorSubject([
    {
      key: 1,
      id: 1,
      firstName: "Mike",
      lastName: "Spencer",
      dateOfBirth: "1990-01-01",
      country: "Canada",
      email: "mike@test.com",
      amount: 0
  },
  {
      key: 2,
      id: 2,
      firstName: "Hellen",
      lastName: "Miller",
      dateOfBirth: "1990-01-01",
      country: "Austria",
      email: "hellen@test.com",
      amount: 0
  },
  {
      key: 3,
      id: 3,
      firstName: "Jack",
      lastName: "Reed",
      dateOfBirth: "1990-01-01",
      country: "United Kingdom",
      email: "jack@test.com",
      amount: 0
  }
  ]);

  constructor() { }

  getDataSource(): Observable<Trader[]> {
    return this.traderList.asObservable();
  }

  getColumns(): string[] {
    return ['firstName', 'lastName', 'email', 'dateOfBirth', 'country', 'actions']
  } 

  getTrader(id: number): any {
    let arr: Trader[] = this.traderList.getValue();
    let trader: Trader;

    arr.forEach((item, index) => {
      if (item.id == id) { 
        trader = item as Trader;
      }
    });

    return trader!;
  }

  deleteTrader(id: number): void {
    let arr: Trader[] = this.traderList.getValue();
    arr.forEach((item, index) => {
      if (item.id == id) { arr.splice(index, 1); }
    });

    this.traderList.next(arr);
  }

  addFunds(id: number, amount: number): void {
    let arr: Trader[] = this.traderList.getValue();
    arr.forEach((item, index) => {
      if (item.id == id) { 
        item.amount = item.amount + amount;
      }
    });

    this.traderList.next(arr);
  }

  removeFunds(id: number, amount:number): void {
    let arr: Trader[] = this.traderList.getValue();
    arr.forEach((item, index) => {
      if (item.id == id) { 
        item.amount = item.amount - amount;
      }
    });

    this.traderList.next(arr);
  }

  addTrader(trader: Trader): void {
    let arr: Trader[] = this.traderList.getValue();

    let latestTrader: Trader = arr[arr.length - 1];
    trader.id = latestTrader.id + 1;
    trader.key = latestTrader.key + 1;
    arr.push(trader);

    this.traderList.next(arr);
  }

  editTrader(id: number, fName: string, lName: string, email: string): void {
    let arr: Trader[] = this.traderList.getValue();
    arr.forEach((item, index) => {
      if (item.id == id) { 

        if (fName && fName != item.firstName) {
          item.firstName = fName;
        }
        
        if (lName && lName != item.lastName) {
          item.lastName = lName;
        }

        if (email && email != item.email) {
          item.email = email;
        }
      }
    });

    this.traderList.next(arr);
  }
}
