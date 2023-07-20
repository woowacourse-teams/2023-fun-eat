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

export const SORT_OPTIONS = [
  { label: '높은 가격순', value: 'price,desc' },
  { label: '낮은 가격순', value: 'price,asc' },
] as const;

export const FOOD_CATEGORY_MENU = [
  { id: 0, name: '즉석조리' },
  { id: 1, name: '과자' },
  { id: 2, name: '간편식사' },
  { id: 3, name: '아이스크림' },
];

export const STORE_CATEGORY_MENU = [
  { id: 0, name: 'CU' },
  { id: 1, name: 'GS25' },
  { id: 2, name: '이마트24' },
];
