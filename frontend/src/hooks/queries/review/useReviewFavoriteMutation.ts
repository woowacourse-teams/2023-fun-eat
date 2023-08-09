import { useMutation } from '@tanstack/react-query';

import { productApi } from '@/apis';
import type { ReviewFavoriteRequestBody } from '@/types/review';

const headers = { 'Content-Type': 'application/json' };

const patchReviewFavorite = (productId: number, reviewId: number, body: ReviewFavoriteRequestBody) => {
  return productApi.patch({ params: `/${productId}/reviews/${reviewId}`, credentials: true }, headers, body);
};

const useReviewFavoriteMutation = (productId: number, reviewId: number) => {
  return useMutation({
    mutationFn: (body: ReviewFavoriteRequestBody) => patchReviewFavorite(productId, reviewId, body),
    onError: () => {
      alert('리뷰 좋아요를 다시 시도해주세요.');
    },
  });
};

export default useReviewFavoriteMutation;
