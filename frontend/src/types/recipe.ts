import type { Member } from './member';
import type { Product } from './product';

export interface RecipeDetail {
  id: number;
  images: string[];
  title: string;
  content: string;
  author: Member;
  products: UsedProduct[];
  totalPrice: number;
  favoriteCount: number;
  favorite: boolean;
  createdAt: string;
}

type UsedProduct = Pick<Product, 'id' | 'name' | 'price'>;
