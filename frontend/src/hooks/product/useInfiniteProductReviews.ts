import { useInfiniteQuery } from '@tanstack/react-query';
import { useState } from 'react';

import { productApi } from '@/apis';
import type { ProductReviewResponse } from '@/types/response';
import type { Review } from '@/types/review';

const useInfiniteProductReviews = (productId: number, sort: string) => {
  const [productReviews, setProductReviews] = useState<Review[]>([]);

  const fetchProductReviews = async ({ pageParam = 0 }) => {
    const res = await productApi.get({
      params: `/${productId}/reviews`,
      queries: `?sort=${sort}&page=${pageParam}`,
      credentials: true,
    });

    const data: ProductReviewResponse = await res.json();
    return data;
  };

  const { fetchNextPage, hasNextPage, isError } = useInfiniteQuery({
    queryKey: [`productReviews-${productId}`],
    queryFn: fetchProductReviews,
    onSuccess: (data) => {
      setProductReviews(data.pages.flatMap((page) => page.reviews));
    },
    getNextPageParam: (prevResponse: ProductReviewResponse) => {
      const isLast = prevResponse.page.lastPage;
      const nextPage = prevResponse.page.requestPage + 1;
      return isLast ? undefined : nextPage;
    },
  });

  return { fetchNextPage, hasNextPage, productReviews, isError };
};

export default useInfiniteProductReviews;
