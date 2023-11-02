import { useMutation, useQueryClient } from '@tanstack/react-query';

import { productApi } from '@/apis';
import { useToastActionContext } from '@/hooks/context';
import type { ReviewFavoriteRequestBody } from '@/types/review';

const headers = { 'Content-Type': 'application/json' };

const patchReviewFavorite = (productId: number, reviewId: number, body: ReviewFavoriteRequestBody) => {
  return productApi.patch({ params: `/${productId}/reviews/${reviewId}`, credentials: true }, headers, body);
};

const useReviewFavoriteMutation = (productId: number, reviewId: number) => {
  const queryClient = useQueryClient();
  const { toast } = useToastActionContext();

  const queryKey = ['product', productId, 'review'];

  return useMutation({
    mutationFn: (body: ReviewFavoriteRequestBody) => patchReviewFavorite(productId, reviewId, body),
    onError: (error) => {
      if (error instanceof Error) {
        toast.error(error.message);
        return;
      }

      toast.error('리뷰 좋아요를 다시 시도해주세요.');
    },
    onSuccess: () => queryClient.invalidateQueries({ queryKey: queryKey }),
  });
};

export default useReviewFavoriteMutation;
