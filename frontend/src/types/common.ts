import type { SvgIconVariant } from '@/components/Common/Svg/SvgIcon';
import type { PRODUCT_SORT_OPTIONS, REVIEW_SORT_OPTIONS } from '@/constants';
import type { PATH } from '@/constants/path';

export type CategoryVariant = 'food' | 'store';

export const isCategoryVariant = (value: string): value is CategoryVariant => {
  return value === 'store' || value === 'food';
};

export interface Category {
  id: number;
  name: string;
}

export interface Tag {
  id: number;
  name: string;
}

export interface NavigationMenu {
  variant: SvgIconVariant;
  name: '검색' | '목록' | '홈' | '꿀조합' | '마이';
  path: (typeof PATH)[keyof typeof PATH] | '/products/food' | '/products/store';
}

export type ProductSortOption = 'price' | 'averageRating' | 'reviewCount';

export type ReviewSortOption = 'favoriteCount' | 'rating';

export type SortOption = (typeof PRODUCT_SORT_OPTIONS)[number] | (typeof REVIEW_SORT_OPTIONS)[number];
