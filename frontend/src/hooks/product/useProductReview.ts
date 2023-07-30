import { useGet } from '../useGet';

import { productApi } from '@/apis';
import type { ProductReviewResponse } from '@/types/response';

const useProductReview = (productId: string, sort: string) => {
  return useGet<ProductReviewResponse>(
    () => productApi.get({ params: `/${productId}/reviews`, queries: `?sort=${sort}` }),
    [sort]
  );
};

export default useProductReview;
