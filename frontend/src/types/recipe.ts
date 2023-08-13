import type { Member } from './member';
import type { Product } from './product';

export type RecipeUsedProduct = Pick<Product, 'id' | 'name'>;

export interface RecipeRequest {
  title: string;
  productIds: number[];
  content: string;
}

export interface RecipePostRequestBody extends FormData {
  images: File[];
  recipeRequest: RecipeRequest;
}

export type RecipeRequestKey = keyof RecipeRequest;

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
