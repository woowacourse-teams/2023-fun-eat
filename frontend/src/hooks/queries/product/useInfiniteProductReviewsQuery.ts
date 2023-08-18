import { useSuspendedInfiniteQuery } from '..';

import { productApi } from '@/apis';
import type { ProductReviewResponse } from '@/types/response';

const fetchProductReviews = async (pageParam: number, productId: number, sort: string) => {
  const res = await productApi.get({
    params: `/${productId}/reviews`,
    queries: `?sort=${sort}&page=${pageParam}`,
    credentials: true,
  });

  const data: ProductReviewResponse = await res.json();
  return data;
};

const useInfiniteProductReviewsQuery = (productId: number, sort: string) => {
  return useSuspendedInfiniteQuery(
    ['product', 'review', productId, sort],
    ({ pageParam = 0 }) => fetchProductReviews(pageParam, productId, sort),
    {
      getNextPageParam: (prevResponse: ProductReviewResponse) => {
        const isLast = prevResponse.page.lastPage;
        const nextPage = prevResponse.page.requestPage + 1;
        return isLast ? undefined : nextPage;
      },
    }
  );
};

export default useInfiniteProductReviewsQuery;
