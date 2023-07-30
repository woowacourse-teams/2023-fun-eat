import { useGet } from '../useGet';

import { tagApi } from '@/apis';
import type { ReviewTag } from '@/types/review';

const useReviewTag = () => {
  return useGet<ReviewTag[]>(() => tagApi.get({}));
};

export default useReviewTag;
