import { useInfiniteQuery } from '@tanstack/react-query';

import { searchApi } from '@/apis';
import type { CategoryProductResponse } from '@/types/response';

const fetchSearchedProducts = async (query: string, page: number) => {
  const response = await searchApi.get({ params: '/products', queries: `?query=${query}&page=${page}` });
  const data: CategoryProductResponse = await response.json();

  return data;
};

const useSearchedProductsQuery = (query: string) => {
  return useInfiniteQuery({
    queryKey: ['search', 'products', query],
    queryFn: ({ pageParam = 0 }) => fetchSearchedProducts(query, pageParam),
    getNextPageParam: (prevResponse: CategoryProductResponse) => {
      const isLast = prevResponse.page.lastPage;
      const nextPage = prevResponse.page.requestPage + 1;
      return isLast ? undefined : nextPage;
    },
  });
};

export default useSearchedProductsQuery;
