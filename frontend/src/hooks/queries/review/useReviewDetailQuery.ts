import { useSuspendedQuery } from '../useSuspendedQuery';

import { reviewApi } from '@/apis';
import type { ReviewDetailResponse } from '@/types/response';

const fetchReviewDetail = async (reviewId: number) => {
  const response = await reviewApi.get({ params: `/${reviewId}` });
  const data: ReviewDetailResponse = await response.json();
  return data;
};

const useReviewDetailQuery = (reviewId: number) => {
  return useSuspendedQuery(['review'], () => fetchReviewDetail(reviewId));
};

export default useReviewDetailQuery;
