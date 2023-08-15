import { useInfiniteQuery } from '@tanstack/react-query';

import { searchApi } from '@/apis';
import type { RecipeResponse } from '@/types/response';

const fetchRecipeSearchResults = async (query: string, page: number) => {
  const response = await searchApi.get({ params: '/recipes/results', queries: `?query=${query}&page=${page}` });
  const data: RecipeResponse = await response.json();

  return data;
};

const useInfiniteRecipeSearchResultsQuery = (query: string) => {
  return useInfiniteQuery({
    queryKey: ['search', 'recipes', query],
    queryFn: ({ pageParam = 0 }) => fetchRecipeSearchResults(query, pageParam),
    getNextPageParam: (prevResponse: RecipeResponse) => {
      const isLast = prevResponse.page.lastPage;
      const nextPage = prevResponse.page.requestPage + 1;
      return isLast ? undefined : nextPage;
    },
    suspense: true,
  });
};

export default useInfiniteRecipeSearchResultsQuery;
