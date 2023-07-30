import { useGet } from '../useGet';

import { tagApi } from '@/apis';
import type { ReviewTag } from '@/types/review';

const useReviewTags = () => {
  return useGet<ReviewTag[]>(() => tagApi.get({}));
};

export default useReviewTags;
