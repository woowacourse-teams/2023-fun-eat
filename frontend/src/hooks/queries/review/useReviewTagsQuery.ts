import { useQuery } from '@tanstack/react-query';

import { tagApi } from '@/apis';
import type { ReviewTag } from '@/types/review';

const fetchReviewTags = async () => {
  const response = await tagApi.get({});
  const data: ReviewTag[] = await response.json();
  return data;
};

const useReviewTagsQuery = () => {
  return useQuery({
    queryKey: ['reviewTags'],
    queryFn: () => fetchReviewTags(),
  });
};

export default useReviewTagsQuery;
