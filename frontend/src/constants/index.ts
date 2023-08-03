import { PATH } from './path';

import type { NavigationMenu } from '@/types/common';

export const NAVIGATION_MENU: NavigationMenu[] = [
  {
    variant: 'search',
    name: '검색',
    path: PATH.SEARCH,
  },
  {
    variant: 'list',
    name: '목록',
    path: `${PATH.PRODUCT_LIST}/food`,
  },
  {
    variant: 'home',
    name: '홈',
    path: PATH.HOME,
  },
  {
    variant: 'recipe',
    name: '꿀조합',
    path: PATH.RECIPE,
  },
  {
    variant: 'profile',
    name: '마이',
    path: PATH.PROFILE,
  },
];

export const PRODUCT_SORT_OPTIONS = [
  { label: '높은 가격순', value: 'price,desc' },
  { label: '낮은 가격순', value: 'price,asc' },
  { label: '높은 평점순', value: 'averageRating,desc' },
  { label: '낮은 평점순', value: 'averageRating,asc' },
  { label: '리뷰 많은순', value: 'reviewCount,desc' },
] as const;

export const REVIEW_SORT_OPTIONS = [
  { label: '최신 작성순', value: 'createdAt,desc' },
  { label: '높은 평점순', value: 'rating,desc' },
  { label: '낮은 평점순', value: 'rating,asc' },
  { label: '추천순', value: 'favoriteCount,desc' },
] as const;

export const TAG_TITLE = {
  TASTE: '맛',
  PRICE: '가격/양',
  ETC: '기타',
} as const;

export const MIN_DISPLAYED_TAGS_LENGTH = 3;
