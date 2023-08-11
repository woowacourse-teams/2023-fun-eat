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
