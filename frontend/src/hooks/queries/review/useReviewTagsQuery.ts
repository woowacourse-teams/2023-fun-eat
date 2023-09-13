import { useSuspendedQuery } from '..';

import { tagApi } from '@/apis';
import type { ReviewTag } from '@/types/review';

const fetchReviewTags = async () => {
  const response = await tagApi.get({});
  const data: ReviewTag[] = await response.json();
  return data;
};

const useReviewTagsQuery = () => {
  return useSuspendedQuery(['review', 'tags'], () => fetchReviewTags());
};

export default useReviewTagsQuery;
