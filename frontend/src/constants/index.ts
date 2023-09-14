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
    variant: 'member',
    name: '마이',
    path: PATH.MEMBER,
  },
];

export const PRODUCT_SORT_OPTIONS = [
  { label: '리뷰 많은순', value: 'reviewCount,desc' },
  { label: '높은 가격순', value: 'price,desc' },
  { label: '낮은 가격순', value: 'price,asc' },
  { label: '높은 평점순', value: 'averageRating,desc' },
  { label: '낮은 평점순', value: 'averageRating,asc' },
] as const;

export const REVIEW_SORT_OPTIONS = [
  { label: '최신 작성순', value: 'createdAt,desc' },
  { label: '높은 평점순', value: 'rating,desc' },
  { label: '낮은 평점순', value: 'rating,asc' },
  { label: '추천순', value: 'favoriteCount,desc' },
] as const;

export const RECIPE_SORT_OPTIONS = [
  { label: '최신 작성순', value: 'createdAt,desc' },
  { label: '추천순', value: 'favoriteCount,desc' },
] as const;

export const TAG_TITLE = {
  TASTE: '맛',
  QUANTITY: '가격/양',
  ETC: '기타',
} as const;

export const MIN_DISPLAYED_TAGS_LENGTH = 3;

export const SEARCH_PAGE_TABS = ['상품', '꿀조합'] as const;

export const CATEGORY_TYPE = {
  FOOD: 'food',
  STORE: 'store',
} as const;

export const IMAGE_MAX_SIZE = 5 * 1024 * 1024;

export const ENVIRONMENT = window.location.href.includes('dev') ? 'dev' : 'prod';

export const IMAGE_URL = process.env.CLOUDFRONT_URL;

export const MENU_NAME = ['간편식사', '과자류', '아이스크림', '식품', '음료'] as const;

export const STORE_NAMES = ['CU', 'GS25', '이마트24', '세븐일레븐'] as const;

export const MENU_IMAGES = [
  `${IMAGE_URL}/${ENVIRONMENT}/food.jpeg`,
  `${IMAGE_URL}/${ENVIRONMENT}/snack.jpeg`,
  `${IMAGE_URL}/${ENVIRONMENT}/icecream.jpeg`,
  `${IMAGE_URL}/${ENVIRONMENT}/ramen.jpeg`,
  `${IMAGE_URL}/${ENVIRONMENT}/tea.jpeg`,
];

export const STORE_IMAGES = [
  `${IMAGE_URL}/${ENVIRONMENT}/cu.jpg`,
  `${IMAGE_URL}/${ENVIRONMENT}/gs.png`,
  `${IMAGE_URL}/${ENVIRONMENT}/emart24.png`,
  `${IMAGE_URL}/${ENVIRONMENT}/seven.png`,
];
