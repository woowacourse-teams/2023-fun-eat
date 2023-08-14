import { useSuspendedInfiniteQuery } from '..';

import { searchApi } from '@/apis';
import type { SearchedProductResponse } from '@/types/response';

const fetchSearchedProducts = async (query: string, page: number) => {
  const response = await searchApi.get({ params: '/products', queries: `?query=${query}&page=${page}` });
  const data: SearchedProductResponse = await response.json();

  return data;
};

const useInfiniteSearchedProductsQuery = (query: string) => {
  return useSuspendedInfiniteQuery(
    ['search', 'products', query],
    ({ pageParam = 0 }) => fetchSearchedProducts(query, pageParam),
    {
      getNextPageParam: (prevResponse: SearchedProductResponse) => {
        const isLast = prevResponse.page.lastPage;
        const nextPage = prevResponse.page.requestPage + 1;
        return isLast ? undefined : nextPage;
      },
    }
  );
};

export default useInfiniteSearchedProductsQuery;
