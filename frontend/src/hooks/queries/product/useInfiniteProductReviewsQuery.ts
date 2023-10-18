import { useSuspendedInfiniteQuery } from '..';

import { productApi } from '@/apis';
import type { ProductReviewResponse } from '@/types/response';

const fetchProductReviews = async (pageParam: number, productId: number, sort: string) => {
  const res = await productApi.get({
    params: `/${productId}/reviews`,
    queries: `?sort=${sort}&lastReviewId=${pageParam}`,
    credentials: true,
  });

  const data: ProductReviewResponse = await res.json();
  return data;
};

const useInfiniteProductReviewsQuery = (productId: number, sort: string) => {
  return useSuspendedInfiniteQuery(
    ['product', productId, 'review', sort],
    ({ pageParam = 0 }) => fetchProductReviews(pageParam, productId, sort),
    {
      getNextPageParam: (prevResponse: ProductReviewResponse) => {
        const lastCursor = prevResponse.reviews.length ? prevResponse.reviews[prevResponse.reviews.length - 1].id : 0;
        return prevResponse.hasNext ? lastCursor : undefined;
      },
    }
  );
};

export default useInfiniteProductReviewsQuery;
