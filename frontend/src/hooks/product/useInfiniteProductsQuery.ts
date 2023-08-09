import { useInfiniteQuery } from '@tanstack/react-query';

import { categoryApi } from '@/apis';
import type { CategoryProductResponse } from '@/types/response';

const useInfiniteProductsQuery = (categoryId: number, sort: string) => {
  const fetchProducts = async ({ pageParam = 0 }) => {
    const res = await categoryApi.get({
      params: `/${categoryId}/products`,
      queries: `?page=${pageParam}&sort=${sort}`,
    });

    const data: CategoryProductResponse = await res.json();
    return data;
  };

  return useInfiniteQuery({
    queryKey: ['products', categoryId],
    queryFn: fetchProducts,
    getNextPageParam: (prevResponse: CategoryProductResponse) => {
      const isLast = prevResponse.page.lastPage;
      const nextPage = prevResponse.page.requestPage + 1;
      return isLast ? undefined : nextPage;
    },
  });
};

export default useInfiniteProductsQuery;
