import { useSuspendedQuery } from '..';

import { categoryApi } from '@/apis';
import type { Category, CategoryVariant, Food, Store } from '@/types/common';

const fetchCategories = async (type: CategoryVariant) => {
  const response = await categoryApi.get({ queries: `?type=${type}` });
  const data: Category[] = await response.json();
  return data;
};

export const useCategoryFoodQuery = (type: Food) => {
  return useSuspendedQuery(['categories', type], () => fetchCategories(type), {
    staleTime: Infinity,
  });
};

export const useCategoryStoreQuery = (type: Store) => {
  return useSuspendedQuery(['categories', type], () => fetchCategories(type), {
    staleTime: Infinity,
  });
};
