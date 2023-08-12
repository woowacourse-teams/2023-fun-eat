import { useQuery } from '@tanstack/react-query';

import { recipeApi } from '@/apis';
import type { RecipeDetail } from '@/types/recipe';

const fetchRecipeDetail = async (recipeId: number) => {
  const response = await recipeApi.get({ params: `/${recipeId}` });
  const data: RecipeDetail = await response.json();
  return data;
};

const useRecipeDetailQuery = (recipeId: number) => {
  return useQuery({
    queryKey: ['recipeDetail'],
    queryFn: () => fetchRecipeDetail(recipeId),
  });
};

export default useRecipeDetailQuery;
