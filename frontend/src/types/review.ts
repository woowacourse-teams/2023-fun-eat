import type { Tag } from './common';

export interface Review {
  id: number;
  userName: string;
  profileImage: string;
  image: string | null;
  rating: number;
  tags: Tag[];
  content: string;
  rebuy: boolean;
  favoriteCount: number;
  favorite: boolean;
}
