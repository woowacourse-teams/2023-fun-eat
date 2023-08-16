import { useMutation } from '@tanstack/react-query';

import { recipeApi } from '@/apis';
import type { RecipeFavoriteRequestBody } from '@/types/recipe';

const headers = { 'Content-Type': 'application/json' };

const patchRecipeFavorite = (recipeId: number, body: RecipeFavoriteRequestBody) => {
  return recipeApi.patch({ params: `/${recipeId}`, credentials: true }, headers, body);
};

const useRecipeFavoriteMutation = (recipeId: number) => {
  return useMutation({
    mutationFn: (body: RecipeFavoriteRequestBody) => patchRecipeFavorite(recipeId, body),
  });
};

export default useRecipeFavoriteMutation;
