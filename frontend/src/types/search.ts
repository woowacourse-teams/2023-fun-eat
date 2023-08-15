import type { Product } from './product';

export interface ProductSearchResult extends Product {
  categoryType: string;
}

export type ProductSearchAutocomplete = Pick<ProductSearchResult, 'id' | 'name' | 'categoryType'>;
