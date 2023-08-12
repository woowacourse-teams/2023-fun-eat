import { useInfiniteQuery } from '@tanstack/react-query';

import { categoryApi } from '@/apis';
import type { CategoryProductResponse } from '@/types/response';

const fetchProducts = async (pageParam: number, categoryId: number, sort = 'reviewCount,desc') => {
  const res = await categoryApi.get({
    params: `/${categoryId}/products`,
    queries: `?page=${pageParam}&sort=${sort}`,
  });

  const data: CategoryProductResponse = await res.json();
  return data;
};

const useInfiniteProductsQuery = (categoryId: number, sort = 'reviewCount,desc') => {
  return useInfiniteQuery({
    queryKey: ['products', categoryId, sort],
    queryFn: ({ pageParam = 0 }) => fetchProducts(pageParam, categoryId, sort),
    getNextPageParam: (prevResponse: CategoryProductResponse) => {
      const isLast = prevResponse.page.lastPage;
      const nextPage = prevResponse.page.requestPage + 1;
      return isLast ? undefined : nextPage;
    },
  });
};

export default useInfiniteProductsQuery;
