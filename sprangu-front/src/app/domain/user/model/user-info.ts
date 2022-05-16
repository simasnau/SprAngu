export class UserInfo {
  id: number;
  name: string;
  password: string;
  role: Role;
}

export class UserDetailed {
  id: number;
  sub: string;
  ROLE: Role;

}

export class Role {
  authority: string;
}

