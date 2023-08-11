import type { Member } from './member';

export interface Recipe {
  id: number;
  image: string;
  title: string;
  author: Member;
  createdAt: string;
  favoriteCount: number;
}
