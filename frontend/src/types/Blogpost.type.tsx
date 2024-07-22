import { Comment } from "./Comment.type";
import { User } from "./User.type";

export interface Blogpost {
  id: number;
  user: User;
  title: string;
  content: string;
  hearts: number;
  createdAt: string;
  comments?: Comment[];
}
