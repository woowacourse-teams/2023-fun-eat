import type { Member } from './member';
import type { Product } from './product';

export interface RecipeDetail extends Recipe {
  images: string[];
  content: string;
  totalPrice: number;
  favorite: boolean;
}
export interface Recipe {
  id: number;
  image: string;
  title: string;
  author: Member;
  products: RecipeProduct[];
  favoriteCount: number;
  createdAt: string;
}

type RecipeProduct = Pick<Product, 'id' | 'name' | 'price'>;
