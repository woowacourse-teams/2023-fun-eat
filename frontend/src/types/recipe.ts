import type { Product } from './product';

export type RecipeUsedProduct = Pick<Product, 'id' | 'name'>;
