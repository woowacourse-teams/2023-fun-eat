import { useSuspendedInfiniteQuery } from '..';

import { memberApi } from '@/apis';
import type { MemberRecipeResponse } from '@/types/response';

const fetchMemberRecipe = async (pageParam: number) => {
  const response = await memberApi.get({ params: '/recipes', queries: `?page=${pageParam}`, credentials: true });
  const data: MemberRecipeResponse = await response.json();
  return data;
};

const useInfiniteMemberRecipeQuery = () => {
  return useSuspendedInfiniteQuery(['member', 'recipes'], ({ pageParam = 0 }) => fetchMemberRecipe(pageParam), {
    getNextPageParam: (prevResponse: MemberRecipeResponse) => {
      const isLast = prevResponse.page.lastPage;
      const nextPage = prevResponse.page.requestPage + 1;
      return isLast ? undefined : nextPage;
    },
  });
};

export default useInfiniteMemberRecipeQuery;
