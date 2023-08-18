import { useSuspendedQuery } from '..';

import { rankApi } from '@/apis';
import type { ReviewRankingResponse } from '@/types/response';

const fetchReviewRanking = async () => {
  const response = await rankApi.get({ params: '/reviews' });
  const data: ReviewRankingResponse = await response.json();
  return data;
};

const useReviewRankingQuery = () => {
  return useSuspendedQuery(['ranking', 'review'], () => fetchReviewRanking());
};

export default useReviewRankingQuery;
