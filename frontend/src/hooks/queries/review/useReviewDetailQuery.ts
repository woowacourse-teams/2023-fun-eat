import { useSuspendedQuery } from '../useSuspendedQuery';

import { reviewApi } from '@/apis';
import type { ReviewDetail } from '@/types/review';

const fetchReviewDetail = async (reviewId: number) => {
  const response = await reviewApi.get({ params: `/${reviewId}` });
  const data: ReviewDetail = await response.json();
  return data;
};

const useReviewDetailQuery = (reviewId: number) => {
  return useSuspendedQuery(['review', reviewId, 'detail'], () => fetchReviewDetail(reviewId));
};

export default useReviewDetailQuery;
