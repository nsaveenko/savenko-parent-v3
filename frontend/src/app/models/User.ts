import {Role} from "./Role";
import {StatusUser} from "./StatusUser";

export class User {
  id: number;
  username: string;
  flName: string;
  password: string;
  roleUserByIdRole: Role;
  statusUserByIdStatus: StatusUser;
  postsCount: number;
  isSubscriber: boolean;
  followersCount: number;
  followingCount: number;
}
