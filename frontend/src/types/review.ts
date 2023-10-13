import type { Tag, TagVariants } from './common';

export interface Review {
  id: number;
  userName: string;
  profileImage: string;
  image: string | null;
  rating: number;
  tags: Tag[];
  content: string;
  createdAt: string;
  rebuy: boolean;
  favoriteCount: number;
  favorite: boolean;
}

export interface ReviewDetail extends Review {
  categoryType: string;
  productId: number;
  productName: string;
}

export interface ReviewTag {
  tagType: TagVariants;
  tags: Tag[];
}

export interface ReviewRequest {
  rating: number;
  tagIds: number[];
  content: string;
  rebuy: boolean;
}

export type ReviewRequestKey = keyof ReviewRequest;

export interface ReviewFavoriteRequestBody {
  favorite: boolean;
}
