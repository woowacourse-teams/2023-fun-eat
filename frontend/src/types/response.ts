import type { Product } from './product';
import type { ProductRanking, ReviewRanking } from './ranking';
import type { Recipe } from './recipe';
import type { Review } from './review';
import type { ProductSearchResult, ProductSearchAutocomplete } from './search';

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

export interface RecipeResponse {
  page: Page;
  recipes: Recipe[];
}

export interface ProductSearchAutocompleteResponse {
  page: Page;
  products: ProductSearchAutocomplete[];
}

export interface ProductSearchResultResponse {
  page: Page;
  products: ProductSearchResult[];
}

export interface MemberReviewResponse {
  page: Page;
  reviews: ReviewRanking[];
}
