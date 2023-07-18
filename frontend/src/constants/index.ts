import type { NavigationMenu } from '@/types/common';

export const NAVIGATION_MENU: NavigationMenu[] = [
  {
    variant: 'search',
    name: '검색',
    path: '',
  },
  {
    variant: 'list',
    name: '목록',
    path: '',
  },
  {
    variant: 'home',
    name: '홈',
    path: '',
  },
  {
    variant: 'recipe',
    name: '꿀조합',
    path: '',
  },
  {
    variant: 'profile',
    name: '마이',
    path: '',
  },
];

export const SORT_OPTIONS = ['높은 가격순', '낮은 가격순'] as const;
