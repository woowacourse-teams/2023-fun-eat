import { useQuery } from '@tanstack/react-query';

import { memberApi } from '@/apis';
import type { MemberReviewResponse } from '@/types/response';

const fetchProfileReview = async () => {
  const response = await memberApi.get({ params: '/reviews' });
  const data: MemberReviewResponse = await response.json();
  return data;
};

const useProfileReviewQuery = () => {
  return useQuery({
    queryKey: ['profileReview'],
    queryFn: () => fetchProfileReview(),
  });
};

export default useProfileReviewQuery;
