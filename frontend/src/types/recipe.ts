import type { Member } from './member';
import type { Product } from './product';

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
  products: RecipeProductWithPrice[];
  favoriteCount: number;
  createdAt: string;
}

export interface RecipeFavoriteRequestBody {
  favorite: boolean;
}

type RecipeProductWithPrice = Pick<Product, 'id' | 'name' | 'price'>;
export type RecipeProduct = Omit<RecipeProductWithPrice, 'price'>;
