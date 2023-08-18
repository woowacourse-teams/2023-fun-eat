import { useSuspendedInfiniteQuery } from '..';

import { recipeApi } from '@/apis';
import type { RecipeResponse } from '@/types/response';

const fetchRecipes = async (pageParam: number, sort: string) => {
  const response = await recipeApi.get({ queries: `?sort=${sort}&page=${pageParam}` });
  const data: RecipeResponse = await response.json();
  return data;
};

const useInfiniteRecipesQuery = (sort: string) => {
  return useSuspendedInfiniteQuery(['recipe', sort], ({ pageParam = 0 }) => fetchRecipes(pageParam, sort), {
    getNextPageParam: (prevResponse: RecipeResponse) => {
      const isLast = prevResponse.page.lastPage;
      const nextPage = prevResponse.page.requestPage + 1;
      return isLast ? undefined : nextPage;
    },
  });
};

export default useInfiniteRecipesQuery;
