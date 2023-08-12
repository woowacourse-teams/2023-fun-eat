import { useQuery } from '@tanstack/react-query';

import { memberApi } from '@/apis';
import type { MemberReviewResponse } from '@/types/response';

const fetchMemberReview = async () => {
  const response = await memberApi.get({ params: '/reviews' });
  const data: MemberReviewResponse = await response.json();
  return data;
};

const useMemberReviewQuery = () => {
  return useQuery({
    queryKey: ['profileReview'],
    queryFn: () => fetchMemberReview(),
  });
};

export default useMemberReviewQuery;
