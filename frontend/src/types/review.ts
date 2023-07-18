import type { Tag } from './common';

export interface Review {
  id: number;
  userName: string;
  profileImage: string;
  image: string;
  rating: number;
  tags: Tag[];
  content: string;
  rebuy: boolean;
  favoriteCount: number;
  favorite: boolean;
}
