import { useMutate } from '../useMutate';

import { productApi } from '@/apis';

const headers = { 'Content-Type': 'application/json' };

const useReviewFavorite = <B>(productId: number, reviewId: number) => {
  return useMutate((body?: B) =>
    productApi.patch({ params: `/${productId}/reviews/${reviewId}`, credentials: true }, headers, body)
  );
};

export default useReviewFavorite;
