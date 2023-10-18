import { useSuspendedQuery } from '../useSuspendedQuery';

import { rankApi } from '@/apis';
import type { Review } from '@/types/review';

const fetchBestReview = async (productId: number) => {
  const response = await rankApi.get({ params: `/products/${productId}/reviews` });

  if (response.status === 204) {
    return null;
  }

  const data: Review = await response.json();
  return data;
};

const useBestReviewQuery = (productId: number) => {
  return useSuspendedQuery(['bestReview', productId], () => fetchBestReview(productId));
};

export default useBestReviewQuery;
