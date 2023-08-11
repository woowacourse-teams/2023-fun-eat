import { useQuery } from '@tanstack/react-query';

import { categoryApi } from '@/apis';
import type { Category } from '@/types/common';

const fetchCategories = async (type: string) => {
  const response = await categoryApi.get({ queries: `?type=${type}` });
  const data: Category[] = await response.json();
  return data;
};

const useCategoryQuery = (type: string) => {
  return useQuery({
    queryKey: ['categories', type],
    queryFn: () => fetchCategories(type),
  });
};

export default useCategoryQuery;
