import type { Member } from './member';
import type { Product } from './product';

export type ProductRanking = Pick<Product, 'id' | 'name' | 'image'> & { categoryType: string };

export interface ReviewRanking {
  reviewId: number;
  productId: number;
  productName: string;
  content: string;
  rating: number;
  favoriteCount: number;
  categoryType: string;
}

export interface RecipeRanking {
  id: number;
  image: string | null;
  title: string;
  author: Member;
  favoriteCount: number;
}
