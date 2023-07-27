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
    path: PATH.PRODUCT_LIST,
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
] as const;

export const REVIEW_SORT_OPTIONS = [
  { label: '높은 평점순', value: 'rating,desc' },
  { label: '낮은 평점순', value: 'rating,asc' },
  { label: '추천순', value: 'favoriteCount,asc' },
] as const;
