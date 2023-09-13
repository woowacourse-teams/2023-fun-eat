import { useSuspendedQuery } from '../useSuspendedQuery';

import { rankApi } from '@/apis';
import type { RecipeRankingResponse } from '@/types/response';

const fetchRecipeRanking = async () => {
  const response = await rankApi.get({ params: '/recipes' });
  const data: RecipeRankingResponse = await response.json();
  return data;
};

const useRecipeRankingQuery = () => {
  return useSuspendedQuery(['ranking', 'recipe'], () => fetchRecipeRanking());
};

export default useRecipeRankingQuery;
