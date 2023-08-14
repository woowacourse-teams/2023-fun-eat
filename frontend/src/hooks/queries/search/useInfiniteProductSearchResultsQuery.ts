import { useInfiniteQuery } from '@tanstack/react-query';

import { searchApi } from '@/apis';
import type { ProductSearchResultResponse } from '@/types/response';

const fetchSearchedProducts = async (query: string, page: number) => {
  const response = await searchApi.get({ params: '/products/results', queries: `?query=${query}&page=${page}` });
  const data: ProductSearchResultResponse = await response.json();

  return data;
};

const useInfiniteProductSearchResultsQuery = (query: string) => {
  return useInfiniteQuery({
    queryKey: ['search', 'products', 'results', query],
    queryFn: ({ pageParam = 0 }) => fetchSearchedProducts(query, pageParam),
    getNextPageParam: (prevResponse: ProductSearchResultResponse) => {
      const isLast = prevResponse.page.lastPage;
      const nextPage = prevResponse.page.requestPage + 1;
      return isLast ? undefined : nextPage;
    },
    suspense: true,
  });
};

export default useInfiniteProductSearchResultsQuery;
