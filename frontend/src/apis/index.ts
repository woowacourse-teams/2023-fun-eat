import { ApiClient } from './ApiClient';

export const categoryApi = new ApiClient('/categories');
export const productApi = new ApiClient('/products');
export const tagApi = new ApiClient('/tags');
export const rankApi = new ApiClient('/ranks');
export const loginApi = new ApiClient('/login');
export const memberApi = new ApiClient('/members');
export const recipeApi = new ApiClient('/recipes');
export const searchApi = new ApiClient('/search');
export const logoutApi = new ApiClient('/logout');
export const reviewApi = new ApiClient('/reviews');
export const bannerApi = new ApiClient('/banners');
