export const PATH = {
  HOME: '/',
  SEARCH: '/search',
  PRODUCT_LIST: '/products',
  MEMBER: '/members',
  RECIPE: '/recipes',
  LOGIN: '/login',
} as const;

export const IMAGE_SRC_PATH = process.env.NODE_ENV === 'development' ? '' : '/images/';
