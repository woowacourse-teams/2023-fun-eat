import { useGet } from '../useGet';

import { productApi } from '@/apis';
import type { ProductReviewResponse } from '@/types/response';

const useProductReview = (productId: string, page = 0, sort: string) => {
  return useGet<ProductReviewResponse>(
    () => productApi.get({ params: `/${productId}/reviews`, queries: `?sort=${sort}&page=${page}` }),
    [page, sort]
  );
};

export default useProductReview;
