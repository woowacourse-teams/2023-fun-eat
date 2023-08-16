import { useSuspendedQuery } from '..';

import { recipeApi } from '@/apis';
import type { RecipeDetail } from '@/types/recipe';

const fetchRecipeDetail = async (recipeId: number) => {
  const response = await recipeApi.get({ params: `/${recipeId}`, credentials: true });
  const data: RecipeDetail = await response.json();
  return data;
};

const useRecipeDetailQuery = (recipeId: number) => {
  return useSuspendedQuery(['recipeDetail', recipeId], () => fetchRecipeDetail(recipeId));
};

export default useRecipeDetailQuery;
