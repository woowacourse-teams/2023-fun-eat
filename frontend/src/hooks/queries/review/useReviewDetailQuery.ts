import { useSuspendedQuery } from '../useSuspendedQuery';

import { reviewApi } from '@/apis';
import type { ReviewDetailResponse } from '@/types/response';

const fetchReviewDetail = async () => {
  const response = await reviewApi.get({ params: '/reviews' });
  const data: ReviewDetailResponse = await response.json();
  return data;
};

const useReviewDetailQuery = () => {
  return useSuspendedQuery(['review'], () => fetchReviewDetail());
};

export default useReviewDetailQuery;
