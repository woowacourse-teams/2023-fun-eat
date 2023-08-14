import { useInfiniteQuery } from '@tanstack/react-query';

import { memberApi } from '@/apis';
import type { MemberReviewResponse } from '@/types/response';

const fetchMemberReview = async (pageParam: number) => {
  const response = await memberApi.get({ params: '/reviews', queries: `?page=${pageParam}`, credentials: true });
  const data: MemberReviewResponse = await response.json();
  return data;
};

const useInfiniteMemberReviewQuery = () => {
  return useInfiniteQuery({
    queryKey: ['member', 'reviews'],
    queryFn: ({ pageParam = 0 }) => fetchMemberReview(pageParam),
    getNextPageParam: (prevResponse: MemberReviewResponse) => {
      const isLast = prevResponse.page.lastPage;
      const nextPage = prevResponse.page.requestPage + 1;
      return isLast ? undefined : nextPage;
    },
  });
};

export default useInfiniteMemberReviewQuery;
