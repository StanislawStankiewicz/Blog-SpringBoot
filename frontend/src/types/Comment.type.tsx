import { User } from "./User.type";
import { Blogpost } from "./Blogpost.type";

export interface Comment {
  id: number;
  user: User;
  blogpost?: Blogpost;
  content: string;
  created_at: string;
}
