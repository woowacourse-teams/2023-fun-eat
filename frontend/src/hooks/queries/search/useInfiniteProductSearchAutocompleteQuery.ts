import { useSuspendedInfiniteQuery } from '..';

import { searchApi } from '@/apis';
import type { ProductSearchAutocompleteResponse } from '@/types/response';

const fetchProductSearchAutocomplete = async (query: string, page: number) => {
  const response = await searchApi.get({ params: '/products', queries: `?query=${query}&page=${page}` });
  const data: ProductSearchAutocompleteResponse = await response.json();

  return data;
};

const useInfiniteProductSearchAutocompleteQuery = (query: string) => {
  return useSuspendedInfiniteQuery(
    ['search', 'products', query],
    ({ pageParam = 0 }) => fetchProductSearchAutocomplete(query, pageParam),
    {
      getNextPageParam: (prevResponse: ProductSearchAutocompleteResponse) => {
        const isLast = prevResponse.page.lastPage;
        const nextPage = prevResponse.page.requestPage + 1;
        return isLast ? undefined : nextPage;
      },
    }
  );
};

export default useInfiniteProductSearchAutocompleteQuery;
