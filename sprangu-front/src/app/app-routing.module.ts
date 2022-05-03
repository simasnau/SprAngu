import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {MainPageComponent} from "./pages/main-page/main-page.component";
import {LoginComponent} from "./pages/login/login.component";
import {ToolDetailsPageComponent} from './pages/tool-details-page/tool-details-page.component';
import {MyToolsComponent} from "./pages/my-tools/my-tools.component";
import { ToolReservationPageComponent } from './pages/tool-reservation-page/tool-reservation-page.component';

const routes: Routes = [
  {path: '', component: MainPageComponent},
  {path: 'register', component: LoginComponent},
  {path: 'tool/:id', component: ToolDetailsPageComponent},
  {path: 'tool/:id/reservation', component: ToolReservationPageComponent},
  {path: 'my-tools/view', component: MyToolsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
