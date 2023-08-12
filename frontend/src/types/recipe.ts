import type { Member } from './member';

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
