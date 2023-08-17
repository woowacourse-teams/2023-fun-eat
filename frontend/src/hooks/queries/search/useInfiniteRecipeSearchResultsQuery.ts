import { useSuspendedInfiniteQuery } from '..';

import { searchApi } from '@/apis';
import type { RecipeResponse } from '@/types/response';

const fetchRecipeSearchResults = async (query: string, page: number) => {
  const response = await searchApi.get({ params: '/recipes/results', queries: `?query=${query}&page=${page}` });
  const data: RecipeResponse = await response.json();

  return data;
};

const useInfiniteRecipeSearchResultsQuery = (query: string) => {
  return useSuspendedInfiniteQuery(
    ['search', 'recipes', query],
    ({ pageParam = 0 }) => fetchRecipeSearchResults(query, pageParam),
    {
      getNextPageParam: (prevResponse: RecipeResponse) => {
        const isLast = prevResponse.page.lastPage;
        const nextPage = prevResponse.page.requestPage + 1;
        return isLast ? undefined : nextPage;
      },
    }
  );
};

export default useInfiniteRecipeSearchResultsQuery;
