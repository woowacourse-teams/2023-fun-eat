import { useSuspendedInfiniteQuery } from '..';

import { searchApi } from '@/apis';
import type { ProductSearchResultResponse } from '@/types/response';

const fetchProductSearchResults = async (query: string, page: number) => {
  const response = await searchApi.get({ params: '/products/results', queries: `?query=${query}&page=${page}` });
  const data: ProductSearchResultResponse = await response.json();

  return data;
};

const useInfiniteProductSearchResultsQuery = (query: string) => {
  return useSuspendedInfiniteQuery(
    ['search', 'products', 'results', query],
    ({ pageParam = 0 }) => fetchProductSearchResults(query, pageParam),
    {
      getNextPageParam: (prevResponse: ProductSearchResultResponse) => {
        const isLast = prevResponse.page.lastPage;
        const nextPage = prevResponse.page.requestPage + 1;
        return isLast ? undefined : nextPage;
      },
    }
  );
};

export default useInfiniteProductSearchResultsQuery;
