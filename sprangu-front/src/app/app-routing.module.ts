import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {MainPageComponent} from "./pages/main-page/main-page.component";
import {LoginComponent} from "./pages/login/login.component";
import { ToolDetailsPageComponent } from './components/tool-details-page/tool-details-page.component';

const routes: Routes = [
  {path: '', component: MainPageComponent},
  {path: 'register', component: LoginComponent},
  {path: 'tool/:id', component: ToolDetailsPageComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
