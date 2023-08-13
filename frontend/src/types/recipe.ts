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

interface RecipeProduct {
  id: number;
  name: string;
  price: number;
}

export interface Recipe {
  id: number;
  image: string;
  title: string;
  author: Member;
  createdAt: string;
  favoriteCount: number;
  products: RecipeProduct[];
}
