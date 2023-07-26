import { useMutate } from '../useMutate';

import { productApi } from '@/apis';

const useReviewFavorite = <T>(productId: number, reviewId: number) => {
  return useMutate((body?: T) => productApi.patch({ params: `/${productId}/reviews/${reviewId}` }, body));
};

export default useReviewFavorite;
