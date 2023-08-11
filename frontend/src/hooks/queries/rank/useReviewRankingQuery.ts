import { useQuery } from '@tanstack/react-query';

import { rankApi } from '@/apis';
import type { ReviewRankingResponse } from '@/types/response';

const fetchReviewRanking = async () => {
  const response = await rankApi.get({ params: '/reviews' });
  const data: ReviewRankingResponse = await response.json();
  return data;
};

const useReviewRankingQuery = () => {
  return useQuery({
    queryKey: ['reviewRanking'],
    queryFn: () => fetchReviewRanking(),
  });
};

export default useReviewRankingQuery;
