import { useGet } from '../useGet';

import { rankApi } from '@/apis';
import type { ReviewRankingResponse } from '@/types/response';

const useReviewRank = () => {
  return useGet<ReviewRankingResponse>(() => rankApi.get({ params: '/reviews' }));
};

export default useReviewRank;
