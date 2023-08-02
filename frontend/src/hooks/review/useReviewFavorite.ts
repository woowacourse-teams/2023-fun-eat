import { useMutate } from '../useMutate';

import { productApi } from '@/apis';

const useReviewFavorite = <B>(productId: number, reviewId: number) => {
  return useMutate((body?: B) =>
    productApi.patch({ params: `/${productId}/reviews/${reviewId}`, credentials: true }, body)
  );
};

export default useReviewFavorite;
