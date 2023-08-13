import { useSuspendedQuery } from '..';

import { categoryApi } from '@/apis';
import type { Category } from '@/types/common';

const fetchCategories = async (type: string) => {
  const response = await categoryApi.get({ queries: `?type=${type}` });
  const data: Category[] = await response.json();
  return data;
};

const useCategoryQuery = (type: string) => {
  return useSuspendedQuery(['categories', type], () => fetchCategories(type));
};

export default useCategoryQuery;
