import type { Product } from './product';
import type { ProductRanking, ReviewRanking } from './ranking';
import type { Review } from './review';
import type { SearchedProduct } from './search';

export interface Page {
  totalDataCount: number;
  totalPages: number;
  firstPage: boolean;
  lastPage: boolean;
  requestPage: number;
  requestSize: number;
}

export interface CategoryProductResponse {
  page: Page;
  products: Product[];
}
export interface ProductReviewResponse {
  page: Page;
  reviews: Review[];
}

export interface ReviewRankingResponse {
  reviews: ReviewRanking[];
}

export interface ProductRankingResponse {
  products: ProductRanking[];
}

export interface SearchedProductResponse {
  page: Page;
  products: SearchedProduct[];
}

export interface MemberReviewResponse {
  page: Page;
  reviews: ReviewRanking[];
}
