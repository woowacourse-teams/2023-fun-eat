import type { Member } from './member';
import type { Product } from './product';

export interface RecipeRequest {
  title: string;
  productIds: number[];
  content: string;
}

export type RecipeRequestKey = keyof RecipeRequest;

export interface RecipeDetail extends Recipe {
  images: string[];
  content: string;
  totalPrice: number;
  favorite: boolean;
}

export interface BaseRecipe {
  id: number;
  image: string | null;
  title: string;
  createdAt: string;
  favoriteCount: number;
}

export interface Recipe extends BaseRecipe {
  author: Member;
  products: RecipeProductWithPrice[];
}

export interface MemberRecipe extends BaseRecipe {
  products: RecipeProduct[];
}

export interface RecipeFavoriteRequestBody {
  favorite: boolean;
}

type RecipeProductWithPrice = Pick<Product, 'id' | 'name' | 'price'>;
export type RecipeProduct = Omit<RecipeProductWithPrice, 'price'>;

export interface Comment {
  id: number;
  author: Member;
  content: string;
  createdAt: string;
}
