import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {RouterModule} from "@angular/router";
import {AppRoutingModule} from "./app-routing.module";
import {MainPageComponent} from './pages/main-page/main-page.component';
import {LoginComponent} from './pages/login/login.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {HeaderComponent} from './components/header/header.component';
import {ToolsForRentalComponent} from './components/tools-for-rental/tools-for-rental.component';
import {ToolDetailsPageComponent} from './pages/tool-details-page/tool-details-page.component';
import {SearchBarComponent} from './components/search-bar/search-bar.component';
import {MyToolsComponent} from './pages/my-tools/my-tools.component';
import {DialogComponent} from './components/dialog/dialog.component';
import {MatDialogModule} from "@angular/material/dialog";
import {BrowserAnimationsModule, NoopAnimationsModule} from "@angular/platform-browser/animations";
import {AuthenticationService} from "./services/authentication.service";
import { ToolReservationPageComponent } from './pages/tool-reservation-page/tool-reservation-page.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  declarations: [
    AppComponent, 
    MainPageComponent,
    LoginComponent,
    HeaderComponent,
    ToolsForRentalComponent,
    ToolDetailsPageComponent,
    SearchBarComponent,
    MyToolsComponent,
    DialogComponent,
    ToolReservationPageComponent,
  ],
  imports: [
    BrowserModule,
    RouterModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule,
    HttpClientModule,
    MatDialogModule,
    NoopAnimationsModule,
    BrowserAnimationsModule
  ],
  exports: [
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
  constructor(private _authService: AuthenticationService) {
    _authService.init();
  }
}
