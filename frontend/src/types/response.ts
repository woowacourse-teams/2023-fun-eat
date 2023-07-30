import type { Product } from './product';
import type { ReviewRanking } from './ranking';
import type { Review } from './review';

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
