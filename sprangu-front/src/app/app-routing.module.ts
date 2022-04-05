import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {MainPageComponent} from "./pages/main/main-page/main-page.component";
import {LoginComponent} from "./pages/user/login/login.component";

const routes: Routes = [
  {path: '', component: MainPageComponent},
  {path: 'register', component: LoginComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
