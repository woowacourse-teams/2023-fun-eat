import { useSuspendedInfiniteQuery } from '../useSuspendedInfiniteQuery';

import { recipeApi } from '@/apis';
import type { CommentResponse } from '@/types/response';

const fetchRecipeComments = async (pageParam: number, recipeId: number) => {
  const response = await recipeApi.get({ params: `/${recipeId}/comments`, queries: `?lastId=${pageParam}` });
  const data: CommentResponse = await response.json();
  return data;
};

const useInfiniteRecipeCommentQuery = (recipeId: number) => {
  return useSuspendedInfiniteQuery(
    ['recipeComment', recipeId],
    ({ pageParam = 0 }) => fetchRecipeComments(pageParam, recipeId),
    {
      getNextPageParam: (prevResponse: CommentResponse) => {
        const lastCursor = prevResponse.comments[prevResponse.comments.length - 1].id;
        return prevResponse.hasNext ? lastCursor : undefined;
      },
    }
  );
};

export default useInfiniteRecipeCommentQuery;
