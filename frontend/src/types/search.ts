import type { Product } from './product';

export interface ProductSearchResults extends Product {
  categoryType: string;
}

export type SearchingProduct = Pick<ProductSearchResults, 'id' | 'name' | 'categoryType'>;
