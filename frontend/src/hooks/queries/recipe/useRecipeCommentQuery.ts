import { useSuspendedQuery } from '../useSuspendedQuery';

import { recipeApi } from '@/apis';
import type { Comment } from '@/types/recipe';

const fetchRecipeComments = async (recipeId: number) => {
  const response = await recipeApi.get({ params: `/${recipeId}/comments` });
  const data: Comment[] = await response.json();
  return data;
};

const useRecipeCommentQuery = (recipeId: number) => {
  return useSuspendedQuery(['recipeComment', recipeId], () => fetchRecipeComments(recipeId));
};

export default useRecipeCommentQuery;
