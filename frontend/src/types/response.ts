import type { Review } from './review';

export interface Page {
  totalDataCount: number;
  totalPages: number;
  isLastPage: boolean;
  isFirstPage: boolean;
  requestPage: number;
  requestSize: number;
}

export interface ProductReviewResponse {
  page: Page;
  reviews: Review[];
}
