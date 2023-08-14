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

export interface BaseRecipe {
  id: number;
  image: string;
  title: string;
  createdAt: string;
  favoriteCount: number;
}

export interface Recipe extends BaseRecipe {
  author: Member;
  products: RecipeProduct[];
}

export interface MemberRecipe extends BaseRecipe {
  products: string[];
}
