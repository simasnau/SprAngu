import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {RouterModule} from "@angular/router";
import {AppRoutingModule} from "./app-routing.module";
import {MainPageComponent} from './pages/main-page/main-page.component';
import {LoginComponent} from './pages/login/login.component';
import {FormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {HeaderComponent} from './components/header/header.component';
import {ToolsForRentalComponent} from './components/tools-for-rental/tools-for-rental.component';
import {ToolDetailsPageComponent} from './components/tool-details-page/tool-details-page.component';
import {SearchBarComponent} from './components/search-bar/search-bar.component';
import {MyToolsComponent} from './pages/my-tools/my-tools.component';
import {DialogComponent} from './components/dialog/dialog.component';
import {MatDialogModule} from "@angular/material/dialog";
import {BrowserAnimationsModule, NoopAnimationsModule} from "@angular/platform-browser/animations";

@NgModule({
  declarations: [
    AppComponent, MainPageComponent,
    LoginComponent,
    HeaderComponent,
    ToolsForRentalComponent,
    ToolDetailsPageComponent,
    SearchBarComponent,
    MyToolsComponent,
    DialogComponent,
  ],
  imports: [
    BrowserModule,
    RouterModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    BrowserModule,
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
}
