import type { Product } from './product';

export type ProductRanking = Pick<Product, 'id' | 'name' | 'image'>;

export interface ReviewRanking {
  reviewId: number;
  productId: number;
  productName: string;
  content: string;
  rating: number;
  favoriteCount: number;
}
