export interface UserModel {
  id: string;
  email: string;
  admin: boolean;
  lastlogin: string | null;
}
