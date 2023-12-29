import { useToastActionContext } from '@fun-eat/design-system';
import { useMutation, useQueryClient } from '@tanstack/react-query';

import { recipeApi } from '@/apis';
import type { RecipeFavoriteRequestBody, RecipeDetail } from '@/types/recipe';

const headers = { 'Content-Type': 'application/json' };

const patchRecipeFavorite = (recipeId: number, body: RecipeFavoriteRequestBody) => {
  return recipeApi.patch({ params: `/${recipeId}`, credentials: true }, headers, body);
};

const useRecipeFavoriteMutation = (recipeId: number) => {
  const queryClient = useQueryClient();
  const { toast } = useToastActionContext();

  const queryKey = ['recipeDetail', recipeId];

  return useMutation({
    mutationFn: (body: RecipeFavoriteRequestBody) => patchRecipeFavorite(recipeId, body),
    onMutate: async (newFavoriteRequest) => {
      await queryClient.cancelQueries({ queryKey: queryKey });

      const previousRequest = queryClient.getQueryData<RecipeDetail>(queryKey);

      if (previousRequest) {
        queryClient.setQueryData(queryKey, () => ({
          ...previousRequest,
          favorite: newFavoriteRequest.favorite,
          favoriteCount: newFavoriteRequest.favorite
            ? previousRequest.favoriteCount + 1
            : previousRequest.favoriteCount - 1,
        }));
      }

      return { previousRequest };
    },
    onError: (error, _, context) => {
      queryClient.setQueryData(queryKey, context?.previousRequest);

      if (error instanceof Error) {
        toast.error(error.message);
        return;
      }

      toast.error('좋아요를 다시 시도해주세요.');
    },
    onSettled: () => {
      queryClient.invalidateQueries({ queryKey: queryKey });
    },
  });
};

export default useRecipeFavoriteMutation;
