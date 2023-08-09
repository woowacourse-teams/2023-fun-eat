import { useInfiniteQuery } from '@tanstack/react-query';

import { productApi } from '@/apis';
import type { ProductReviewResponse } from '@/types/response';

const useInfiniteProductReviewsQuery = (productId: number, sort: string) => {
  const fetchProductReviews = async ({ pageParam = 0 }) => {
    const res = await productApi.get({
      params: `/${productId}/reviews`,
      queries: `?sort=${sort}&page=${pageParam}`,
      credentials: true,
    });

    const data: ProductReviewResponse = await res.json();
    return data;
  };

  return useInfiniteQuery({
    queryKey: ['productReviews', productId],
    queryFn: fetchProductReviews,
    getNextPageParam: (prevResponse: ProductReviewResponse) => {
      const isLast = prevResponse.page.lastPage;
      const nextPage = prevResponse.page.requestPage + 1;
      return isLast ? undefined : nextPage;
    },
  });
};

export default useInfiniteProductReviewsQuery;
