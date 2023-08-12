import type { Product } from './product';

export interface SearchedProduct extends Product {
  category: string;
}
