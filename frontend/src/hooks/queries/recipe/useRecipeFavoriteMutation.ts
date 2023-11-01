import { useMutation, useQueryClient } from '@tanstack/react-query';

import { recipeApi } from '@/apis';
import { useToastActionContext } from '@/hooks/context';
import type { RecipeFavoriteRequestBody } from '@/types/recipe';

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

      const previousRequest = queryClient.getQueryData(queryKey);
      queryClient.setQueryData(queryKey, newFavoriteRequest);

      return { previousRequest };
    },
    onError: (error, _, context) => {
      queryClient.setQueryData(queryKey, context?.previousRequest);
      if (error instanceof Error) {
        toast.error(error.message);
        return;
      }

      toast.error('꿀조합 좋아요를 다시 시도해주세요.');
    },
    onSettled: () => queryClient.invalidateQueries({ queryKey: queryKey }),
  });
};

export default useRecipeFavoriteMutation;
