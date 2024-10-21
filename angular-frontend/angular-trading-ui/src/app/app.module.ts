import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { NavbarComponent } from './navbar/navbar.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table'
import { MatDialogModule } from '@angular/material/dialog'
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { MatDatepickerModule } from '@angular/material/datepicker'
import { MatNativeDateModule } from '@angular/material/core';
import { TraderListComponent } from './trader-list/trader-list.component';
import { AddTraderDialogComponent } from './add-trader-dialog/add-trader-dialog.component';
import { TraderAccountComponent } from './trader-account/trader-account.component';
import { AddFundsDialogComponent } from './add-funds-dialog/add-funds-dialog.component';
import { RemoveFundsDialogComponent } from './remove-funds-dialog/remove-funds-dialog.component';
import { TraderListService } from './trader-list.service';
import { QuotesListComponent } from './quotes-list/quotes-list.component';
import { HttpClientModule } from '@angular/common/http';
import { EditTraderDialogComponent } from './edit-trader-dialog/edit-trader-dialog.component';

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    NavbarComponent,
    TraderListComponent,
    AddTraderDialogComponent,
    TraderAccountComponent,
    AddFundsDialogComponent,
    RemoveFundsDialogComponent,
    QuotesListComponent,
    EditTraderDialogComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatIconModule,
    MatButtonModule,
    MatTableModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    ReactiveFormsModule,
    MatDatepickerModule,
    MatNativeDateModule,
    HttpClientModule
  ],
  providers: [TraderListService],
  bootstrap: [AppComponent]
})
export class AppModule { }
