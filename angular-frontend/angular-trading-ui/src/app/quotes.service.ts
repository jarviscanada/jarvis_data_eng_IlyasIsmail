import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, map } from 'rxjs';
import { Quote } from './quote';

@Injectable({
  providedIn: 'root'
})
export class QuotesService {

  private url: string = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=IBM&apikey=demo";


  quoteList = new BehaviorSubject<any[]>([{
    "Global Quote": {
      "01. symbol": "IBM",
      "02. open": "236.4000",
      "03. high": "237.3700",
      "04. low": "232.7100",
      "05. price": "232.9600",
      "06. volume": "3350556",
      "07. latest trading day": "2024-10-15",
      "08. previous close": "235.2600",
      "09. change": "-2.3000",
      "10. change percent": "-0.9776%"
    }
  },
  {
    "Global Quote": {
      "01. symbol": "MSFT",
      "02. open": "415.1700",
      "03. high": "416.3600",
      "04. low": "410.4800",
      "05. price": "416.1200",
      "06. volume": "15508932",
      "07. latest trading day": "2024-10-16",
      "08. previous close": "418.7400",
      "09. change": "-2.6200",
      "10. change percent": "-0.6257%"
    }
  }]);

  constructor(private http: HttpClient) { }

  getQuotes() {
    //without API
    return this.quoteList.asObservable();

    //with API
    return this.http.get<any>(this.url).pipe(
      map(response => {
        return [{
          "Global Quote": {
            ["01. symbol"]: response["Global Quote"]["01. symbol"],
            ["02. open"]: response["Global Quote"]["02. open"],
            ["03. high"]: response["Global Quote"]["03. high"],
            ["04. low"]: response["Global Quote"]["04. low"],
            ["05. price"]: response["Global Quote"]["05. price"],
            ["06. volume"]: response["Global Quote"]["06. volume"],
            ["07. latest trading day"]: response["Global Quote"]["07. latest trading day"],
            ["08. previous close"]: response["Global Quote"]["08. previous close"],
            ["09. change"]: response["Global Quote"]["09. change"],
            ["10. change percent"]: response["Global Quote"]["10. change percent"],
          }
        }]
      }));
  }

  getColumns(): string[] {
    return ['symbol', 'open', 'high', 'low', 'price', 'volume', 'latestTradingDay', 'previousClose', 'change', 'changePercent']
  }
}
