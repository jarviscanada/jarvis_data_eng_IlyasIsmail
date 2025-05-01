import { Component, OnInit } from '@angular/core';
import { TraderListService } from '../trader-list.service';
import { FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-remove-funds-dialog',
  templateUrl: './remove-funds-dialog.component.html',
  styleUrls: ['./remove-funds-dialog.component.css']
})
export class RemoveFundsDialogComponent implements OnInit {

  id!: number;

  constructor(private service: TraderListService, private builder: FormBuilder) { }

  ngOnInit(): void {
  }

  withdrawForm = this.builder.group({
    amount: this.builder.control('')
  })

  removeFunds():void {
    this.service.removeFunds(this.id, this.withdrawForm.value.amount)
  }

}
