import { useMutation, useQueryClient } from '@tanstack/react-query';

import { recipeApi } from '@/apis';

interface RecipeCommentRequestBody {
  comment: string;
}

const headers = { 'Content-Type': 'application/json' };

const postRecipeComment = (recipeId: number, body: RecipeCommentRequestBody) => {
  return recipeApi.post({ params: `/${recipeId}/comments`, credentials: true }, headers, body);
};

const useRecipeCommentMutation = (recipeId: number) => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: (body: RecipeCommentRequestBody) => postRecipeComment(recipeId, body),
    onSuccess: () => queryClient.invalidateQueries({ queryKey: ['recipeComment', recipeId] }),
  });
};

export default useRecipeCommentMutation;
