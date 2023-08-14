import { useInfiniteQuery } from '@tanstack/react-query';

import { searchApi } from '@/apis';
import type { SearchedProductResponse } from '@/types/response';

const fetchSearchedProducts = async (query: string, page: number) => {
  const response = await searchApi.get({ params: '/products/results', queries: `?query=${query}&page=${page}` });
  const data: SearchedProductResponse = await response.json();

  return data;
};

const useInfiniteProductSearchResultsQuery = (query: string) => {
  return useInfiniteQuery({
    queryKey: ['search', 'products', 'result', query],
    queryFn: ({ pageParam = 0 }) => fetchSearchedProducts(query, pageParam),
    getNextPageParam: (prevResponse: SearchedProductResponse) => {
      const isLast = prevResponse.page.lastPage;
      const nextPage = prevResponse.page.requestPage + 1;
      return isLast ? undefined : nextPage;
    },
    suspense: true,
  });
};

export default useInfiniteProductSearchResultsQuery;
