import {Component, OnInit} from '@angular/core';
import {UserInfo} from "../model/user-info";
import {UserService} from "../user.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  model = new UserInfo();

  constructor(
    private userService: UserService
  ) {
  }

  ngOnInit(): void {
  }

  register(): void {
    this.userService.createUser(this.model).subscribe();
  }
}
