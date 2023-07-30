import type { Tag, TagNameOption } from './common';

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

export interface ReviewTag {
  tagType: TagNameOption;
  tags: Tag[];
}

export interface ReviewRequest {
  rating: number;
  tags: number[];
  content: string;
  rebuy: boolean;
  memberId: number;
}

export interface ReviewPostRequestBody extends FormData {
  image: File;
  reviewRequest: ReviewRequest;
}

export interface ReviewFavoriteRequestBody {
  favorite: boolean;
  memberId: number;
}
