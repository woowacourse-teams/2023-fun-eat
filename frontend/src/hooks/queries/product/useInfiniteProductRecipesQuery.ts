import { useSuspendedInfiniteQuery } from '../useSuspendedInfiniteQuery';

import { productApi } from '@/apis';
import type { RecipeResponse } from '@/types/response';

const fetchProductRecipes = async (pageParam: number, productId: number, sort: string) => {
  const response = await productApi.get({
    params: `/${productId}/recipes`,
    queries: `?sort=${sort}&page=${pageParam}`,
    credentials: true,
  });
  const data: RecipeResponse = await response.json();
  return data;
};

const useInfiniteProductRecipesQuery = (productId: number, sort: string) => {
  return useSuspendedInfiniteQuery(
    ['product', 'recipes', productId, sort],
    ({ pageParam = 0 }) => fetchProductRecipes(pageParam, productId, sort),
    {
      getNextPageParam: (prevResponse: RecipeResponse) => {
        const isLast = prevResponse.page.lastPage;
        const nextPage = prevResponse.page.requestPage + 1;
        return isLast ? undefined : nextPage;
      },
    }
  );
};

export default useInfiniteProductRecipesQuery;
