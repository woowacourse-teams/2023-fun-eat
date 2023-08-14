import type { Product } from './product';

export interface ProductSearchResults extends Product {
  categoryType: string;
}

export type ProductSearch = Pick<ProductSearchResults, 'id' | 'name' | 'categoryType'>;
