import { ApiClient } from './ApiClient';

export const categoryApi = new ApiClient('/categories');
export const productApi = new ApiClient('/products');
export const tagApi = new ApiClient('/tags');
export const rankApi = new ApiClient('/ranks');
export const loginApi = new ApiClient('/login');
